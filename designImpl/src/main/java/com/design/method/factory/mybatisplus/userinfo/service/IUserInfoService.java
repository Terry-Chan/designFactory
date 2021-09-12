package com.design.method.factory.mybatisplus.userinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.design.method.api.Response.Response;
import com.design.method.factory.mybatisplus.userinfo.pojo.UserInfo;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author terry
 * @since 2021-08-02
 */
public interface IUserInfoService extends IService<UserInfo> {

    Response addUserInfo(UserInfo userInfo);

    Response deleteUserInfoById(Integer age);

    Response<List<UserInfo>>  selectUserInfoByMap(Integer age);

    Response  updateUserInfoByMap(UserInfo userInfo);

}
