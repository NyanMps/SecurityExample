package com.bfchengnuo.security.demo.web.async;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 异步请求处理
 *
 * 流程模拟（订单处理）：
 * 服务 1 （本程序）接收 http 请求后交给线程 1 处理；线程 1 发送消息到 MQ，由另一个服务（服务 2）来监听并处理；
 * 请求处理完成后，服务 2 将结果放回 MQ，服务 1 监听到结果由线程 2 处理，返回 http 响应。
 *
 * 使用 DeferredResult 的方式，由 MVC 提供
 *
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
@RestController
@RequestMapping("/async")
@AllArgsConstructor
@Slf4j
public class AsyncController {
    private final MockQueue MOCK_QUEUE;
    private final DeferredResultHolder DEFERRED_RESULT_HOLDER;

    private static String call() {
        log.info("子线程处理开始");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("子线程处理结束");
        return "success";
    }

    @GetMapping
    public Callable<String> callable() {
        log.info("主线程处理开始");
        Callable<String> result = AsyncController::call;
        log.info("主线程处理结束");
        return result;
    }

    @GetMapping("/order")
    public DeferredResult<String> order() {
        log.info("主线程处理开始");
        String random = RandomStringUtils.random(8);
        MOCK_QUEUE.setPlaceOrder(random);

        DeferredResult<String> deferredResult = new DeferredResult<>();
        DEFERRED_RESULT_HOLDER.getMap().put(random, deferredResult);
        log.info("主线程处理结束");

        return deferredResult;
    }
}
