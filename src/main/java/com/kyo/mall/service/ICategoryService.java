package com.kyo.mall.service;

import com.kyo.mall.pojo.Category;
import com.kyo.mall.vo.CategoryVo;
import com.kyo.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
