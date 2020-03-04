package com.kyo.mall.controller;

import com.kyo.mall.pojo.Category;
import com.kyo.mall.service.ICategoryService;
import com.kyo.mall.vo.CategoryVo;
import com.kyo.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll(){
        return categoryService.selectAll();
    }
}
