package com.bfchengnuo.security.core.validate.code.processor;

import com.bfchengnuo.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 校验码处理器的默认抽象实现
 * 处理器的基本职责：
 * 根据现有的验证码生成器，负责生成、校验校验码
 *
 * 校验码生成器会有多个存在，通过泛型 C 来指定某一个类型
 *
 * 生成验证码的最后一个步骤-发送为空实现，交由子类负责实现
 *
 * @author 冰封承諾Andy
 * @date 2019-08-22
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor{
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;


    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(ServletWebRequest request) {
        ValidateCodeType processorType = getValidateCodeType(request);

        C codeInSession = (C) validateCodeRepository.get(request, processorType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            validateCodeRepository.remove(request, processorType);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        validateCodeRepository.remove(request, processorType);
    }

    /**
     * 生成校验码
     * 跟前请求中的参数，寻找与之对应的验证码生成器，然后进行生成
     *
     * @param request 请求对象
     * @return 泛型指定的验证码类型实体
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();

        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码到 Session
     * 为了兼容 Redis 方案，需要对象可序列化，这里只存校验码和过期时间
     *
     * @param request 请求对象
     * @param validateCode 泛型指定的验证码实体
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType(request));
    }

    /**
     * 获取验证码保存到 session 中的 key
     * 组成：固定前缀 + 验证码类型
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 获取校验码的类型
     * 根据类名前面的规范（SMS or IMAGE）
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
}
