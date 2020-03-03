package com.kyo.mall.dao;

import com.kyo.mall.MallApplicationTests;
import com.kyo.mall.pojo.Category;
import org.apache.catalina.mapper.Mapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CategoryMapperTest extends MallApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findById() {
        Category byId = categoryMapper.findById(100001);
        System.out.println(byId);
    }

    @Test
    public void queryById() {
        Category byId = categoryMapper.queryById(100001);
        System.out.println(byId);
    }
}