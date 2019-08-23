package com.bfchengnuo.security.core.validate.code.processor;

import com.bfchengnuo.security.core.validate.code.ValidateCodeException;
import com.bfchengnuo.security.core.validate.code.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 验证码处理器选择器
 *
 * 根据 ValidateCodeType 的名称或者直接传入字符串名称来定位处理器 class
 * 处理器需要规范命名
 *
 * @author 冰封承諾Andy
 * @date 2019-08-19
 */
@Component
public class ValidateCodeProcessorHolder {
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }
}
