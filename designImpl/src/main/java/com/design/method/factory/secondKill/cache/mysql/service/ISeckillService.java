package com.design.method.factory.secondKill.cache.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.design.method.factory.secondKill.dto.Seckill;

import java.util.List;

/**
 * <p>
 * 秒杀库存表 服务类
 * </p>
 *
 * @author terry
 * @since 2021-08-10
 */
public interface ISeckillService extends IService<Seckill> {

    public List<Seckill> getList(String productId);

    public void update(Seckill seckill);

}
