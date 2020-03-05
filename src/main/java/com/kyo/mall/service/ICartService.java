package com.kyo.mall.service;

import com.kyo.mall.form.CartAddForm;
import com.kyo.mall.vo.CartVo;
import com.kyo.mall.vo.ResponseVo;

public interface ICartService {

    ResponseVo<CartVo> add(Integer uid,CartAddForm form);

    ResponseVo<CartVo> list(Integer uid);
}
