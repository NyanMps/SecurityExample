package com.bfchengnuo.security.app.validate;

import com.bfchengnuo.security.core.validate.code.ValidateCode;
import com.bfchengnuo.security.core.validate.code.ValidateCodeException;
import com.bfchengnuo.security.core.validate.code.ValidateCodeRepository;
import com.bfchengnuo.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 校验码存储逻辑 -- redis 实现
 * 通过请求头的 deviceId 作为 key
 *
 * @author 冰封承諾Andy
 * @date 2019-11-10
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
    }


    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }


    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }


    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带 deviceId 参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }

}
