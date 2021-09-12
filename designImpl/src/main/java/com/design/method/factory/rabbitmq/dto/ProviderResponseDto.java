package com.design.method.factory.rabbitmq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderResponseDto {
    private String id;
}
