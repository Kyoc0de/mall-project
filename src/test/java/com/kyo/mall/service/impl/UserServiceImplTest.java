package com.kyo.mall.service.impl;

import com.kyo.mall.MallApplicationTests;
import com.kyo.mall.enums.RoleEnum;
import com.kyo.mall.pojo.User;
import com.kyo.mall.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
public class UserServiceImplTest extends MallApplicationTests {
    public static final String USERNAME = "jack";

    public static final String PASSWORD = "123456";
    @Autowired
    private IUserService userService;
    @Test
    public void register() {
        User user = new User(USERNAME, PASSWORD, "jack@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }
}