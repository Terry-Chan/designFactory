package com.design.method.factory.rabbitmq.provider.service;

import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.api.snowflake.SnowId;
import com.design.method.factory.rabbitmq.dto.ProviderRquestDto;
import com.design.method.factory.rabbitmq.provider.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RabbitMqServiceImpl implements RabbitMqService{


    //由于配置类RabbitMqConfig配置类中的rabbitTemplate的
    //scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SnowId snowId;

    @Autowired
    public RabbitMqServiceImpl(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        //设置回调为当前类对象
        rabbitTemplate.setConfirmCallback(this);
    }


    @Override
    public String send(ProviderRquestDto message, String exchange, String queueRoutingKey) throws Exception {


        String callBackId = String.valueOf(snowId.nextId());
        CorrelationData correlationId = new CorrelationData(callBackId);
        log.info("开始发送消息内容:{}",message.toString());
        //发送消息到消息队列
        message.setUniqueId(callBackId);
        rabbitTemplate.convertAndSend(exchange, queueRoutingKey, message, correlationId);
        log.info("发送定制的回调id:{}",callBackId);
        return callBackId;

    }



    /**
     * 消息回调确认方法
     * @param correlationData 请求数据对象
     * @param ack 是否发送成功
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info(" 回调id:" + correlationData.getId());
        if (ack) {
            log.info("消息发送成功");
        } else {
            log.info("消息发送失败:" + cause);
        }
    }
}
