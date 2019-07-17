package com.bfchengnuo.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 样例程序-启动类
 * 自动生成 API 文档地址： http://localhost:8080/swagger-ui.html
 *
 * 禁用认证：
 * 使用 @SpringBootApplication(exclude = {
 *         SecurityAutoConfiguration.class,
 *         ManagementWebSecurityAutoConfiguration.class})
 *
 * @author Created by 冰封承諾Andy
 */
@SpringBootApplication(scanBasePackages = "com.bfchengnuo.security")
@RestController
@EnableSwagger2
public class SecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
