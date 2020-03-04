package com.kyo.mall.service;

import com.kyo.mall.pojo.User;
import com.kyo.mall.vo.ResponseVo;

public interface IUserService {

    ResponseVo<User> register(User user);

    ResponseVo<User> login(String username,String password);
}
