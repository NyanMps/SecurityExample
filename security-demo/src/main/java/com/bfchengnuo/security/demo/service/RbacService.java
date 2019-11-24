package com.bfchengnuo.security.demo.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权示例代码
 *
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
public interface RbacService {
    /**
     * 检查访问的资源是否具有权限
     *
     * @param request
     * @param authentication
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
