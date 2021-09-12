package com.design.method.factory.security.controller;

import com.design.method.api.Response.Response;
import com.design.method.factory.security.dto.LoginRequest;
import com.design.method.factory.security.dto.LoginRespons;
import com.design.method.factory.security.service.LoginService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static com.design.method.factory.secondKill.util.AuthUtil.*;


@RestController
@RequestMapping("/security")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @RequestMapping("/login")
    public ResponseEntity<LoginRespons> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response){
        //todo  登陆逻辑自定义
        checkRqeust(request,"入参数不能为空");
        checkAgreement(request.getNickName(),"用户名不能为空");
        checkAgreement(request.getPwd(),"密码不能为空");
        checkAgreement(request.getPhone(),"手机号码不能为空");
        LoginRespons body = loginService.login(request);
        Map map = Maps.newHashMap();
        map.put("token",body.getTokens());
        httpServletRequest.setAttribute("Headers",map);
        return ResponseEntity.ok(body);
    }

    @RequestMapping("/test")
    public Response test() {
        return new Response().SUCCESS("可以访问接口了！");
    }


}
