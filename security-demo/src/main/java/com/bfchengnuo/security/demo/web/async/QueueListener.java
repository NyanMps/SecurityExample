package com.bfchengnuo.security.demo.web.async;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

/**
 * 自定义 MQ 的监听器
 * 当 {@link MockQueue} 中的 completeOrder 有值时（订单处理完毕），返回 http 响应
 *
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
@Component
@AllArgsConstructor
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    private final MockQueue MOCK_QUEUE;
    private final DeferredResultHolder DEFERRED_RESULT_HOLDER;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 当容器初始化完毕后执行
        new Thread(() -> {
            // 模拟 MQ 的监听
            while (true) {
                if (StringUtils.isNotBlank(MOCK_QUEUE.getCompleteOrder())) {
                    DeferredResult<String> deferredResult = DEFERRED_RESULT_HOLDER.getMap().get(MOCK_QUEUE.getCompleteOrder());
                    // 处理完成时，设置 Result
                    deferredResult.setResult("place order success");
                    log.info("订单【" + MOCK_QUEUE.getCompleteOrder() + "】处理完成");

                    MOCK_QUEUE.setCompleteOrder(null);
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "thread-order-2").start();
    }
}
