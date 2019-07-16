package com.bfchengnuo.security.demo.web.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 模拟消息队列
 * 当 completeOrder 有值时，代表订单处理完毕
 *
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
@Data
@Component
@Slf4j
public class MockQueue {
    private String placeOrder;
    private String completeOrder;

    public void setPlaceOrder(String placeOrder) {
        // 模拟处理订单
        new Thread(()->{
            log.info("收到下单请求：" + placeOrder);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(placeOrder + "订单处理完毕");
            this.completeOrder = placeOrder;
        }, "thread-order-1").start();
    }
}
