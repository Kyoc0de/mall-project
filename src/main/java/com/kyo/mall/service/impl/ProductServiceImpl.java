package com.kyo.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kyo.mall.dao.ProductMapper;
import com.kyo.mall.enums.ProductStatusEnum;
import com.kyo.mall.pojo.Product;
import com.kyo.mall.service.ICategoryService;
import com.kyo.mall.service.IProductService;
import com.kyo.mall.vo.ProductDetailVo;
import com.kyo.mall.vo.ProductVo;
import com.kyo.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kyo.mall.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        //创建id set集合
        Set<Integer> categoryIdSet = new HashSet<>();
        if(categoryId != null){
            //调用查询子类目id的方法获取全部相关id
            categoryService.findSubCategoryId(categoryId,categoryIdSet);
            //并将传入id也添加到id set集合中
            categoryIdSet.add(categoryId);
        }

        PageHelper.startPage(pageNum,pageSize);

        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList = productList.stream()
                .map(e->{
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e,productVo);
                    return productVo;
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        //只对确定性条件判断
        if (product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())
                || product.getStatus().equals(ProductStatusEnum.DELETE.getCode())) {
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        //敏感数据处理
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
