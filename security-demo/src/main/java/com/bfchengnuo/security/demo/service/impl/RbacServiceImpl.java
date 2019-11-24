package com.bfchengnuo.security.demo.service.impl;

import com.bfchengnuo.security.demo.service.RbacService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
@Service("rbacService")
public class RbacServiceImpl implements RbacService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;

        // 如果是匿名访问，principal 则是个字符串
        if (principal instanceof UserDetails) {
            //如果用户名是admin，就永远返回true
            if (StringUtils.equals(((UserDetails) principal).getUsername(), "admin")) {
                hasPermission = true;
            } else {
                // TODO 读取用户所拥有权限的所有 URL
                // 这里需要根据自己定义的 User 对象来进行获取
                // Set<String> urls = ((Admin) principal).getUrls();
                Set<String> urls = new HashSet<>();
                for (String url : urls) {
                    if (antPathMatcher.match(url, request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }

        return hasPermission;
    }
}
