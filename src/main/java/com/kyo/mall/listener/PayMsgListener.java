package com.kyo.mall.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "payNotify")
public class PayMsgListener {

    @RabbitHandler
    public void process(String msg){
        log.info("接收消息={}",msg);
    }
}
