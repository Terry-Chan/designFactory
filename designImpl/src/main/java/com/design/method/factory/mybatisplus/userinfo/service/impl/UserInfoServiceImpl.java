package com.design.method.factory.mybatisplus.userinfo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.design.method.api.Response.Response;
import com.design.method.factory.mybatisplus.userinfo.mapper.UserInfoMapper;
import com.design.method.factory.mybatisplus.userinfo.pojo.UserInfo;
import com.design.method.factory.mybatisplus.userinfo.service.IUserInfoService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.sql.DataSourceDefinition;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author terry
 * @since 2021-08-02
 */
@Service
@DS("source_a")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    @GlobalTransactional
    public Response addUserInfo(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
        return new Response().SUCCESS(null);
    }


    @Override
    public Response deleteUserInfoById(Integer age) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.le(age < 18,"age",age);
        userInfoMapper.delete(wrapper);
        return new Response().SUCCESS(null);
    }

    @Override
    public Response<List<UserInfo>> selectUserInfoByMap(Integer age) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.ge(age > 18,"age",age);
        List<UserInfo> list = (List)userInfoMapper.selectMaps(wrapper);
        return new Response().SUCCESS(list);
    }

    @Override
    @GlobalTransactional
    public Response updateUserInfoByMap(UserInfo userInfo) {
        UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("age",userInfo.getAge());
        userInfoMapper.update(userInfo,wrapper);
        return new Response().SUCCESS(null);
    }


}
