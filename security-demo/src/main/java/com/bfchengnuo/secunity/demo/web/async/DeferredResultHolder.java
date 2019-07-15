package com.bfchengnuo.secunity.demo.web.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于两个线程之间的数据传递, 使用 DeferredResult 的方式
 *
 * @see AsyncController
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
@Data
@Component
public class DeferredResultHolder {
    private Map<String, DeferredResult<String>> map = new HashMap<>();
}
