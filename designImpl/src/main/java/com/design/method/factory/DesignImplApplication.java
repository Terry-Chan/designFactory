package com.design.method.factory;



import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.design.method.factory")
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan("com.design.method.factory")
public class DesignImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesignImplApplication.class);
    }

}
