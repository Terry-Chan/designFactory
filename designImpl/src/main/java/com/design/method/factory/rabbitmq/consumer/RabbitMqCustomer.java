package com.design.method.factory.rabbitmq.consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RabbitMqCustomer {

    @RabbitListener(queues = RabbitMqCustomConfig.QUEUE_A)
    @RabbitHandler
    public void doTask(Channel channel, Message message){
        log.info("处理任务==》" + message.getBody().toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("消费成功==》" + message.getBody().toString());
        } catch (IOException e) {
            log.info("消费失败了==》" + message.getBody().toString());
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
