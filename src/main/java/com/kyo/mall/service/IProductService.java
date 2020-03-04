package com.kyo.mall.service;

import com.github.pagehelper.PageInfo;
import com.kyo.mall.vo.ProductDetailVo;
import com.kyo.mall.vo.ProductVo;
import com.kyo.mall.vo.ResponseVo;

import java.util.List;

public interface IProductService {
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);
}
