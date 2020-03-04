package com.kyo.mall.service.impl;

import com.kyo.mall.dao.CategoryMapper;
import com.kyo.mall.pojo.Category;
import com.kyo.mall.service.ICategoryService;
import com.kyo.mall.vo.CategoryVo;
import com.kyo.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kyo.mall.consts.MallConst.ROOT_PARENT_ID;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {

        //获取全部查询数据
        List<Category> categories = categoryMapper.selectAll();

        //对初始化数据惊醒筛选
        //1.root目录
        //2.将需要属性复制到目标对象中
        //3.对数据排序
        //4.得到返回数据list

        //5.调用查询子目录方法 => 传入原始数据和1级数据
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
        findSubCategory(categoryVoList, categories);

        //返回封装数据
        return ResponseVo.success(categoryVoList);


    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        //首先获取全部数据
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id,resultSet,categories);
    }

    private void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categories) {

        for(Category category :categories){
            if(category.getParentId().equals(id)){
                //判断数据中父id等于给定参数id的值并将其id加入到resultSet当中
                resultSet.add(category.getId());
                //继续向下查询
                findSubCategoryId(category.getId(),resultSet,categories);
            }
        }
    }

    //子数据查询
    //传入id, 和上一次查询数据


    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {
        //得到一次筛选结果 和 原始数据 开始子数据查询
        //遍历一次筛选结果
        for (CategoryVo categoryVo : categoryVoList) {
            //新建子数据列表 用于存储子数据
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            //遍历原始数据
            for (Category category : categories) {
                //对比原始数据中 parentId 等于 子数据的
                //转换数据 并添加到子数据list中
                //如果查到内容，设置subCategory, 继续往下查
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
                //数据排序
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                //讲子子数据添加到子数据中
                categoryVo.setSubCategories(subCategoryVoList);
                //再次查询
                findSubCategory(subCategoryVoList, categories);
            }
        }
    }

    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

}
