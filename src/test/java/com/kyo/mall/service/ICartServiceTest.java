package com.kyo.mall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kyo.mall.MallApplicationTests;
import com.kyo.mall.form.CartAddForm;
import com.kyo.mall.vo.CartVo;
import com.kyo.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
@Slf4j
public class ICartServiceTest extends MallApplicationTests {

    @Autowired
    private ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        CartAddForm form = new CartAddForm();
        form.setSelected(true);
        form.setProductId(27);
        cartService.add(1,form);
    }

    @Test
    public void test(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info("list={}",gson.toJson(list));
    }
}