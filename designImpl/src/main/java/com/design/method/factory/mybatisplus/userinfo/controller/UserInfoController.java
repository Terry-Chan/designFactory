package com.design.method.factory.mybatisplus.userinfo.controller;


import com.design.method.api.Response.Response;
import com.design.method.factory.mybatisplus.userinfo.pojo.UserInfo;
import com.design.method.factory.mybatisplus.userinfo.service.impl.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author terry
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/userinfo/user-info")
public class UserInfoController {


   @Autowired
   private UserInfoServiceImpl userInfoService;

    @RequestMapping("addUserInfo")
    public Response addUserInfo(@RequestBody UserInfo userInfo) {
        return userInfoService.addUserInfo(userInfo);
    }

    @RequestMapping("updateUserInfo")
    public Response updateUserInfo(@RequestBody UserInfo userInfo) {
        return userInfoService.updateUserInfoByMap(userInfo);
    }

    @RequestMapping("selectUserInfo")
    public Response<List<UserInfo>>  selectUserInfo(@RequestParam Integer age) {
       return userInfoService.selectUserInfoByMap(age);
    }

    @RequestMapping("deleteUserInfo")
    public Response deleteUserInof(@RequestParam Integer age) {
        return userInfoService.deleteUserInfoById(age);
    }

}

