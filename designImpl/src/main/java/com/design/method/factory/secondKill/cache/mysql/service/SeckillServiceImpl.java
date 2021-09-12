package com.design.method.factory.secondKill.cache.mysql.service;



import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.design.method.factory.secondKill.cache.mysql.mapper.SeckillMapper;
import com.design.method.factory.secondKill.dto.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 秒杀库存表 服务实现类
 * </p>
 *
 * @author terry
 * @since 2021-08-10
 */
@Service
@DS("source_a")
public class SeckillServiceImpl<T> extends ServiceImpl<SeckillMapper, Seckill> implements ISeckillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Override
    public List<Seckill> getList(String productId) {
        QueryWrapper<Seckill> wrapper = new QueryWrapper<>();
        wrapper.eq("seckill_id", productId);
        return seckillMapper.selectList(wrapper);
    }

    @Override
    public void update(Seckill seckill) {
        seckillMapper.updateById(seckill);
    }


}
