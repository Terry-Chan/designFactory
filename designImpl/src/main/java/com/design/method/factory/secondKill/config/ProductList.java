package com.design.method.factory.secondKill.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.design.method.factory.secondKill.dto.SeckillProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


@ConfigurationProperties(prefix = "seckill.productinfo")
@RefreshScope
@Data
@Component
public class ProductList {
    public List<SeckillProduct> list;
}

