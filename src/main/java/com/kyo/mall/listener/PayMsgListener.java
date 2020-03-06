package com.kyo.mall.listener;

import com.google.gson.Gson;
import com.kyo.mall.pojo.PayInfo;
import com.kyo.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "payNotify")
public class PayMsgListener {
    @Autowired
    private IOrderService orderService;
    @RabbitHandler
    public void process(String msg){
        log.info("接收消息={}",msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if(payInfo.getPlatformStatus().equals("SUCCESS")){
            //修改订单里状态
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
