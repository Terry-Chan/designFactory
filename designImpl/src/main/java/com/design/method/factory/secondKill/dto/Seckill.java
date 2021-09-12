package com.design.method.factory.secondKill.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 秒杀库存表
 * </p>
 *
 * @author terry
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Seckill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品库存id
     */
    @TableId(value = "seckill_id", type = IdType.AUTO)
    private Long seckillId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 库存数量
     */
    private Integer inventory;

    /**
     * 秒杀开启时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date endTime;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date createTime;

    private Long version;


}
