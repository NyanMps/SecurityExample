package com.bfchengnuo.security.core.social;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.social.support.CustomizeSpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交登陆的配置主类
 *
 * 默认情况下，所有的 /auth 都会被 {@link org.springframework.social.security.SocialAuthenticationFilter} 拦截，根据 ConnectionFactory 的 providerId 进行匹配
 * 例如 /auth/qq 就会匹配到 {@link com.bfchengnuo.security.core.social.qq.connect.QQConnectionFactory} 处理
 *
 * @see com.bfchengnuo.security.core.social.qq.config.QQAutoConfig
 *
 * @author 冰封承諾Andy
 * @date 2019-09-07
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // 自定义数据仓储，这里设置不加密数据
        // 此表可以通过 set  方法自定义前缀
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());

        if (connectionSignUp != null) {
            // 自动完成注册
            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    /**
     * 社交登录配置类，供浏览器或 app 模块引入设计登录配置用。
     *
     * 使用：例如在 browser 中使用 apply 进行应用，原理也是添加过滤器
     *
     * 当存在 {@link org.springframework.social.connect.ConnectionSignUp} 的实现类时，会自动执行它来获取 userID 自动完成注册，不再进行跳转
     *
     * @return 配置类对象
     */
    @Bean
    public SpringSocialConfigurer springSocialSecurityConfig() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        CustomizeSpringSocialConfigurer customizeSpringSocialConfigurer = new CustomizeSpringSocialConfigurer(filterProcessesUrl);
        // 找不到用户时，跳转到指定的注册（绑定）页面
        customizeSpringSocialConfigurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return customizeSpringSocialConfigurer;
    }

    /**
     * 用来处理注册流程的工具类
     * 用来获取 social 得到的授权信息
     *
     * @param connectionFactoryLocator SB 已经配置好了
     * @return 工具类对象
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(
                connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)) {};
    }
}
