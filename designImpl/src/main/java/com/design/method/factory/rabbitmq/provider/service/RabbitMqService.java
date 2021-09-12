package com.design.method.factory.rabbitmq.provider.service;

import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.rabbitmq.dto.ProviderRquestDto;
import org.springframework.amqp.rabbit.core.RabbitMessageOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface RabbitMqService extends RabbitTemplate.ConfirmCallback {


    /**
     * 发送消息到rabbitmq消息队列
     * @param message 消息内容
     * @param exchange 交换配置
     * @param queueRoutingKey routingKey的队列
     * @throws Exception
     */
    String send(ProviderRquestDto message, String exchange, String queueRoutingKey) throws Exception;

}
