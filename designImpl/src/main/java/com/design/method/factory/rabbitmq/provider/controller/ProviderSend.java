package com.design.method.factory.rabbitmq.provider.controller;


import com.design.method.api.Response.Response;
import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.rabbitmq.dto.ProviderResponseDto;
import com.design.method.factory.rabbitmq.dto.ProviderRquestDto;
import com.design.method.factory.rabbitmq.provider.config.RabbitMqConfig;
import com.design.method.factory.rabbitmq.provider.service.RabbitMqService;
import com.design.method.factory.rabbitmq.provider.service.RabbitMqServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.design.method.api.errordict.ErrorMsgMap.SEND_MSG_MQ_ERROR;

@RestController
@RequestMapping("/mq/provider")
@Slf4j
public class ProviderSend {

    @Autowired
    private RabbitMqServiceImpl service;

    @RequestMapping("/send")
    public Response<List<ProviderResponseDto>> sendMessage(@RequestBody ProviderRquestDto rquestDto) {

        try {

            String id = service.send(rquestDto, RabbitMqConfig.EXCHANGE_A, RabbitMqConfig.ROUTINGKEY_A);
            ProviderResponseDto res = new ProviderResponseDto();
            res.setId(id);
         return new Response<List<ProviderResponseDto>>().SUCCESS(Arrays.asList(res));
        }catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new Response<List<ProviderResponseDto>>().ERROR(SEND_MSG_MQ_ERROR);
        }

    }
}
