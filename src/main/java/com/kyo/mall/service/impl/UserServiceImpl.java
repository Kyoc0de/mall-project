package com.kyo.mall.service.impl;

import com.kyo.mall.dao.UserMapper;
import com.kyo.mall.enums.ResponseEnum;
import com.kyo.mall.enums.RoleEnum;
import com.kyo.mall.pojo.User;
import com.kyo.mall.service.IUserService;
import com.kyo.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {

        //username 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername>0){
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        //email 不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail>0){
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }
        //md5算法摘要
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
//        默认role
        user.setRole(RoleEnum.CUSTOMER.getCode());
        //写入数据库

        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if(user==null){
            return  ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        if(!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            return  ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        };
        user.setPassword("");
        return ResponseVo.success(user);
    }


}
