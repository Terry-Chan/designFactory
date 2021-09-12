package com.design.method.factory.security.service;

import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.security.authentication.SecurityAuthenticationToken;
import com.design.method.factory.security.dto.LoginRequest;
import com.design.method.factory.security.dto.LoginRespons;
import com.design.method.factory.security.jwt.JwtUtil;
import com.google.common.collect.Maps;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {


    @Autowired
    private JwtUtil jwtUtil;

    private static Long expireTime = 600000L;

    @Autowired
    private AuthenticationManager authenticationManager;


    public LoginRespons login(LoginRequest loginRequest) {
        //todo 具体实现逻辑
        LoginRespons respons = new LoginRespons();

        //使用security的认证管理器进行认证
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new SecurityAuthenticationToken(loginRequest.getUsername(), loginRequest.getPhone()));
        } catch (Exception e) {
            throw new DesignMethodFactoryException(-1);
        }

        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String,String> map = Maps.newHashMap();
        map.put("userName", loginRequest.getUsername());
        map.put("phone",loginRequest.getPhone());

        String jtoken = jwtUtil.creatJwtToken(map, expireTime);

        //生成token
        //todo 不需要 存储这个token，没有采用缓存的方式
        respons.setExpireSecond(expireTime);
        respons.setTokens(jtoken);
        respons.setUserId(loginRequest.getUserId());
        return respons;
    }
}
