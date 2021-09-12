package com.design.method.factory.secondKill.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.design.method.api.Response.Response;
import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.secondKill.config.ProductList;
import com.design.method.factory.secondKill.dto.PayOrder;
import com.design.method.factory.secondKill.dto.RequestMakeOrder;


import com.design.method.factory.secondKill.dto.SeckillProduct;
import com.design.method.factory.secondKill.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.design.method.factory.secondKill.util.AuthUtil.*;


/**
 * <p>
 * 秒杀库存表 前端控制器
 * </p>
 *
 * @author terry
 * @since 2021-08-10
 */
@RestController
@RequestMapping("/promotion")
public class SeckillController {


    @Autowired
    private OrderService orderService;

    @Autowired
    public ProductList list;


    @RequestMapping("/secKill")
    public ResponseEntity<PayOrder> makeOrder(@RequestBody RequestMakeOrder request) {

        //限制流量，检查入参,下单
        try {
            checkLimit("系统繁忙，请稍后再试！");
            checkRqeust(request,"入参数不能为空");
            checkAgreement(request.getPhoneNumber(), "手机号不能为空");
            checkAgreement(request.getProductId(), "产品ID不能为空");
            checkAgreement(request.getUserId(),"客户ID不能为空");
            checkProuductId(request.getProductId(),list.getList(), "该产品不参与抢购");
            return new ResponseEntity(orderService.makeOrder(request), HttpStatus.OK);
        }catch (DesignMethodFactoryException e) {
            return new ResponseEntity(e.getMsg(),HttpStatus.EXPECTATION_FAILED);
        }
    }


}

