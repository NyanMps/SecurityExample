package com.bfchengnuo.security.core.validate.code;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
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

/**
 * 验证码相关的请求处理
 *
 * @author Created by 冰封承諾Andy on 2019/7/25.
 */
@RestController
@RequestMapping("/code")
public class ValidateCodeController {
    public static final String SESSION_KEY = "IMAGE_CODE";
    /**
     * 处理 session 的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @Resource
    private ValidateCodeGenerator validateCodeGenerator;

    @GetMapping("/image")
    public void createCode(HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session) throws IOException {
        // 获取参数可用：ServletRequestUtils.getIntParameter()
        ImageCode imageCode = validateCodeGenerator.generate(new ServletWebRequest(request));
        session.setAttribute(SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
