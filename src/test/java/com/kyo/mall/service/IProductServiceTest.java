package com.kyo.mall.service;

import com.kyo.mall.MallApplicationTests;
import com.kyo.mall.enums.ResponseEnum;
import com.kyo.mall.vo.ProductDetailVo;
import com.kyo.mall.vo.ProductVo;
import com.kyo.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class IProductServiceTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;
//
//    @Test
//    public void list() {
//        ResponseVo<List<ProductVo>> responseVo = productService.list(null, 1, 1);
//        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
//    }
@Test
public void detail() {
    ResponseVo<ProductDetailVo> responseVo = productService.detail(26);
    Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
}
}