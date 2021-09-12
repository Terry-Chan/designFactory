package com.design.method.factory.secondKill.service;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.api.snowflake.SnowId;
import com.design.method.factory.secondKill.cache.SecCache;
import com.design.method.factory.secondKill.dto.PayOrder;
import com.design.method.factory.secondKill.dto.RequestMakeOrder;
import com.design.method.factory.secondKill.dto.Seckill;
import com.design.method.factory.secondKill.dto.SeckillProduct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.design.method.api.errordict.ErrorMsgMap.*;
import static com.design.method.factory.rabbitmq.provider.config.RabbitMqConfig.*;

/**-1,先获取锁
 *0，先检查用户是否有下过该产品的订单
 * 1，根据商品ID检查，商品是否抢购完（分布式锁），如果抢购完了就直接返回
 * 2, 先生成一个订单唯一ID
 * 3，生成订单，然后扣减库存，库存扣减成功之后 推送消息到MQ
 */
@Service
@Slf4j
public class OrderService implements RabbitTemplate.ConfirmCallback{

    @Autowired
    private SnowId snowId;

    @Autowired
    private RedissonClient redissonClient;

    private static final Integer DECR_STEP = 1;

    private static final String PRE_FIX = "RPODUCTID=";

    private static final String PAY_ORDER_FIX = "PAY_ORDER=";

    private static final String PRE_FIX_RLOCK_KEY = "PROMOTIONID=";

    private RabbitTemplate rabbitTemplate;

    @Resource(name = "secCacheImpl")
    private SecCache<Seckill> secCache;

    @Resource(name = "secCacheImpl")
    private SecCache<PayOrder> orderSecCache;


    @Autowired
    public OrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public  PayOrder makeOrder(RequestMakeOrder request) throws DesignMethodFactoryException {
        RLock rLock = redissonClient.getLock(PRE_FIX_RLOCK_KEY + request.getProductId());
        PayOrder payOrder = null;
       try {
            if (rLock.tryLock(3,10, TimeUnit.SECONDS)) {
                    //检查用户是否下过单
                    String orderKey = PAY_ORDER_FIX + request.getPhoneNumber();
                    checkOrderkey(orderKey);

                    //获取产品库存信息
                    Seckill seckill = getProductStoreInfo(request.getProductId());

                    //生成订单信息
                    payOrder = new PayOrder();
                    payOrder.setState(0)
                            .setUserPhone(Long.valueOf(request.getPhoneNumber()))
                            .setCreateTime(getCurrentTime())
                            .setSeckillId(Long.valueOf(request.getProductId()))
                            .setUserId(request.getUserId())
                            .setOrderId(snowId.nextId());

                    //扣减库存
                    decreaseStore(seckill);

                    //保存订单信息
                    saveOrderInfo(orderKey, payOrder);

            } else {
                throw new DesignMethodFactoryException(SYSTEM_MAXTHREAD_WARN,"系统繁忙请稍后再试");
            }
       }catch (DesignMethodFactoryException e){
           throw e;
       }catch (Exception e) {
           log.error("下单失败{}", e.getMessage());
           throw new DesignMethodFactoryException(MAKE_ORDER_FAIL,"下单失败！");
       }
       finally {
           rLock.unlock();
       }
       return payOrder;
    }

    private void saveOrderInfo(String orderKey, PayOrder payOrder) {
        orderSecCache.insert(orderKey, payOrder);

        //发送消息到消息队列
        CorrelationData correlationId = new CorrelationData();
        correlationId.setId(payOrder.getUserId());
        rabbitTemplate.convertAndSend(MQ_SECOND_EXCHANGE, MQ_ROUTING_KEY, payOrder, correlationId);
        log.info("订单回调ID:{}",payOrder.getOrderId());
    }

    private void checkOrderkey(String orderKey) throws DesignMethodFactoryException{
       List<PayOrder> result = orderSecCache.select(orderKey, PayOrder.class);
       if ( !(result == null || result.size() <= 0)) {
           throw new DesignMethodFactoryException(CAN_NOT_REPEAT_OREDR,"不能重复下单");
       }
    }

    //扣减库存
    private void decreaseStore(Seckill seckill) {
        //redis  扣减
        seckill.setInventory(seckill.getInventory() - DECR_STEP);
        System.out.println("库存数::==>" + (seckill.getInventory()));
        secCache.update(PRE_FIX+ seckill.getSeckillId().toString(), seckill);

    }


    //检查库存是否还有
    private Seckill getProductStoreInfo(String productId) throws DesignMethodFactoryException{
            List<Seckill> seckillList =  secCache.select(PRE_FIX + productId, Seckill.class);
            Seckill seckill = seckillList.get(0);
           if ( seckill.getInventory() >= 1 ) {
                return seckill;
           } else {
               throw new DesignMethodFactoryException(PRODUCT_SOLD_OUT_ALREADY, "该商品抢购结束");
           }
    }


    /**
     * 消息回调确认方法
     * @param correlationData 请求数据对象
     * @param ack 是否发送成功
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("订单推送到MQ成功,订单号{}",correlationData.getId());
        } else {
            log.info("订单信息发送失败:" + cause);
        }
    }


    private String getCurrentTime() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        return date.format(fmt);
    }

}
