package com.design.method.factory.secondKill.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 秒杀成功明细表
 * </p>
 *
 * @author terry
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PayOrder implements Serializable {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 秒杀商品id
     */
    private Long seckillId;

    /**
     * 用户手机号
     */
    private Long userPhone;

    /**
     * 状态标示:-1:无效 0:成功 1:已付款 2:已发货
     */
    private Integer state;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private String createTime;

    /**
     * 用户ID
     */
    private String userId;



}
