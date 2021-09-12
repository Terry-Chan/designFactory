package com.design.method.factory.secondKill.cache.mysql.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.design.method.factory.secondKill.cache.mysql.mapper.PayOrderMapper;
import com.design.method.factory.secondKill.dto.PayOrder;
import com.design.method.factory.secondKill.dto.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀成功明细表 服务实现类
 * </p>
 *
 * @author terry
 * @since 2021-08-10
 */
@Service
@DS("source_a")
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {


    @Autowired
    private PayOrderMapper payOrderMapper;


    public void insert(PayOrder payOrder) {
        payOrderMapper.insert(payOrder);
    }

    public void update(PayOrder payOrder) {
        payOrderMapper.updateById(payOrder);
    }

    public PayOrder select(String phoneNumber, String seckill_id){
        QueryWrapper<PayOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone",phoneNumber)
                .eq("seckill_id",seckill_id);
        return payOrderMapper.selectList(wrapper).get(0);
    }

}
