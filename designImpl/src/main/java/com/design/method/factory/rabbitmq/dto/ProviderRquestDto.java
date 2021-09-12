package com.design.method.factory.rabbitmq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ProviderRquestDto implements Serializable {

    private String uniqueId = null;

    private String createTime;

    private String deadLine;

    private String handleStatus;
}
