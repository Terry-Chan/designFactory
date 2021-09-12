package com.design.method.factory.secondKill.mqcustom;

import com.design.method.factory.rabbitmq.consumer.RabbitMqCustomConfig;
import com.design.method.factory.secondKill.cache.mysql.MysqlCache;
import com.design.method.factory.secondKill.dto.PayOrder;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SecondKillMqCustom {


    @Autowired
    private MysqlCache<PayOrder> mysqlCache;

    @RabbitListener(queues = "SECOND_QUEUE")
    @RabbitHandler
    public void doTask(Channel channel,Message message){
        Long orderId = 0L;
        try {
            String pushdto = new String(message.getBody());
            Gson gson = new Gson();
            PayOrder payOrder = gson.fromJson(pushdto, PayOrder.class);
            orderId = payOrder.getOrderId();
            mysqlCache.insert(String.valueOf(orderId), gson.fromJson(pushdto, PayOrder.class));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("订单同步完成，订单id:{}", payOrder.getOrderId());
        } catch (IOException e) {
            log.info("订单信息同步失败了，订单id:{}", orderId);

            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            } catch (IOException ex) {
                log.error(ex.getMessage());
                log.error("订单同步失败:{}" + orderId);
            }

            log.error(e.getMessage());
            log.error("订单同步失败:{}" + orderId);
        }

    }
}
