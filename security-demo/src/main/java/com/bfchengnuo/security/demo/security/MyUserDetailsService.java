package com.bfchengnuo.security.demo.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 自定义处理用户登陆的逻辑
 *
 * @author 冰封承諾Andy
 * @date 2019-09-08
 */
@Component("myUserDetailsService")
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // BCryptPasswordEncoder 每次生成的会不一样，应该在注册的时候保存，这里直接拿数据库保存的
        String pwd = passwordEncoder.encode("123123");
        System.out.println("PWD：" + pwd);
        // 简单实现
        return new User(username, pwd,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        String pwd = passwordEncoder.encode("123123");
        return new SocialUser(userId, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
