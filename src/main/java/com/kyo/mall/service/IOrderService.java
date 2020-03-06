package com.kyo.mall.service;

import com.kyo.mall.vo.OrderVo;
import com.kyo.mall.vo.ResponseVo;

public interface IOrderService {

    ResponseVo<OrderVo> create(Integer uid,Integer shippingId);
}
