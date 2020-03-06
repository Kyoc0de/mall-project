package com.kyo.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.kyo.mall.dao.ShippingMapper;
import com.kyo.mall.enums.ResponseEnum;
import com.kyo.mall.form.ShippingForm;
import com.kyo.mall.pojo.Shipping;
import com.kyo.mall.service.IShippingService;
import com.kyo.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form,shipping);
        int row = shippingMapper.insertSelective(shipping);
        if(row == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        Map<String,Integer> map = new HashMap<>();
        map.put("shippingId",shipping.getId());


        return ResponseVo.success(map);
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByIdAndUid(shippingId, uid);
        if(row == 0){
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm form) {

        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form,shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if(row == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();

    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        return null;
    }
}
