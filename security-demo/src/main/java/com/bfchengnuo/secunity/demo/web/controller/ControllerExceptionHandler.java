package com.bfchengnuo.secunity.demo.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 *
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleRuntimeException(RuntimeException exception) {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "自定义 " + exception.getMessage());
        exception.printStackTrace();
        return result;
    }
}
