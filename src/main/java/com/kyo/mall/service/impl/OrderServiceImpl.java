package com.kyo.mall.service.impl;

import com.kyo.mall.dao.OrderMapper;
import com.kyo.mall.dao.ProductMapper;
import com.kyo.mall.dao.ShippingMapper;
import com.kyo.mall.enums.ProductStatusEnum;
import com.kyo.mall.enums.ResponseEnum;
import com.kyo.mall.pojo.Cart;
import com.kyo.mall.pojo.OrderItem;
import com.kyo.mall.pojo.Product;
import com.kyo.mall.pojo.Shipping;
import com.kyo.mall.service.ICartService;
import com.kyo.mall.service.IOrderService;
import com.kyo.mall.vo.OrderVo;
import com.kyo.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {

        //收货地址校验(总要查出来)
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if(shipping == null){
            //说明没有该地址
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);

        }
        //获取购物车,校验商品库存
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
            //是否有商品 及 库存
            //获取cartList里的Products
        Set<Integer> productIdSet = cartList.stream().map(Cart::getProductId).collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        Map<Integer,Product> map = productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        for(Cart cart : cartList){
           //根据productId查询数据
            Product product = map.get(cart.getProductId());
            //是否有该商品
            if(product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,"商品不存在, productId = "+cart.getProductId());
            }
            //是否商品下架
            if(ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,"商品不是在售状态 "+ product.getName());
            }

            //库存是否充足
            if(product.getStock()<cart.getQuantity()){
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,"库存不正确: "+product.getName());
            }

            Long orderNo = generateOrderNo();
            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);

        }
        //计算价格total,只计算选中

        //生成订单,入库 order and orderItem -> 先orderItem 后 order使用事务保证同时成功 同时失败

        //减库存

        //更新购物车

        //返回前端



        return null;
    }

    private Long generateOrderNo() {
        //企业级分布式唯一id -> 有待学习
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNo, Integer quantity, Product product) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
