package com.design.method.factory.secondKill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RequestMakeOrder {

    private String nickName;

    private String userId;

    private String phoneNumber;

    private String productId;

}
