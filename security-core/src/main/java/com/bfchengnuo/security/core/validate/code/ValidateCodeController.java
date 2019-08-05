package com.bfchengnuo.security.core.validate.code;

import com.bfchengnuo.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 验证码相关的请求处理
 *
 * 使用的 {@link ServletWebRequest} 是 Spring 的包装，不仅仅可以包装 Request
 * TODO 短信验证码生成未实现
 *
 * @author Created by 冰封承諾Andy on 2019/7/25.
 */
@RestController
@RequestMapping("/code")
public class ValidateCodeController {
    public static final String SESSION_KEY = "IMAGE_CODE";
    public static final String SESSION_SMS_KEY = "SMS_CODE";
    /**
     * 处理 session 的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @Resource
    private ValidateCodeGenerator imageCodeGenerator;
    @Resource
    private ValidateCodeGenerator smsCodeGenerator;
    @Resource
    private SmsCodeSender smsCodeSender;
    /**
     * 如果使用了 @Autowired 类似注解 Spring 会自动注入多个 ValidateCodeGenerator 到 map
     */
    private Map<String, ValidateCodeGenerator> map;

    @GetMapping("/image")
    public void createCode(HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session) throws IOException {
        // 获取参数可用：ServletRequestUtils.getIntParameter()
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        session.setAttribute(SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/sms")
    public void createSmsCode(HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session) throws ServletRequestBindingException {
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        session.setAttribute(SESSION_KEY, smsCode);
        String phone = ServletRequestUtils.getRequiredStringParameter(request, "phone");
        smsCodeSender.send(phone, smsCode.getCode());
    }
}
