package com.kyo.mall;

import com.kyo.mall.consts.MallConst;
import com.kyo.mall.enums.ResponseEnum;
import com.kyo.mall.exception.UserLoginException;
import com.kyo.mall.pojo.User;
import com.kyo.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            log.info("user=null");
            throw new UserLoginException();


        }
        return true;
    }
}