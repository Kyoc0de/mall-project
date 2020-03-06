package com.kyo.mall.service;

import com.kyo.mall.MallApplicationTests;
import com.kyo.mall.enums.ResponseEnum;
import com.kyo.mall.form.ShippingForm;
import com.kyo.mall.pojo.Shipping;
import com.kyo.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class IShippingServiceTest extends MallApplicationTests {

    @Autowired
    private IShippingService shippingService;

    private Integer uid = 1;

    @Test
    public void add() {

        ShippingForm form = new ShippingForm();
        form.setReceiverName("廖师兄");
        form.setReceiverAddress("慕课网");
        form.setReceiverCity("北京");
        form.setReceiverMobile("18812345678");
        form.setReceiverPhone("010123456");
        form.setReceiverProvince("北京");
        form.setReceiverDistrict("海淀区");
        form.setReceiverZip("000000");
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        log.info("result={}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void list() {
    }
}