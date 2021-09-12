package com.design.method.factory.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginRespons {

    private String userId;

    private String tokens;

    private Long expireSecond;

}
