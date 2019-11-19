package com.bfchengnuo.security.demo.web.controller;

import com.bfchengnuo.security.app.social.AppSingUpUtils;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.demo.dto.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 基本的 RESTful 示例，使用了 @JsonView 多视图
 *
 * @author Created by 冰封承諾Andy on 2019/7/8.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final ProviderSignInUtils providerSignInUtils;
    private final AppSingUpUtils appSingUpUtils;
    private final SecurityProperties securityProperties;

    @GetMapping()
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "查询用户列表")
    public List<User> query(@PageableDefault(size = 11, page = 1) Pageable pageable) {
        System.out.println("PageNumber:" + pageable.getPageNumber());
        System.out.println("PageSize:" + pageable.getPageSize());
        System.out.println("Sort:" + pageable.getSort());

        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }

    @GetMapping("/{id::\\d+}")
    @JsonView(User.UserDetailView.class)
    @ApiOperation(value = "查询单个用户")
    public User getInfo(@ApiParam("用户ID") @PathVariable String id) {
        return new User("1", "name".concat(id), "pwd", new Date());
    }

    @PostMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "新增单个用户")
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                System.out.println(fieldError.getField()
                        .concat(" ")
                        .concat(Objects.requireNonNull(fieldError.getDefaultMessage())));
            });
        }

        Date date = new Date(LocalDateTime.now()
                .plusYears(1L)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli());
        return user;
    }

    /**
     * 获取当前登陆的用户信息
     *
     * 可以通过 SecurityContextHolder.getContext().getAuthentication() 来进行获取
     * 有条件可以直接注入 Authentication 的方式来获取；
     * 或者使用 @AuthenticationPrincipal 注解选择性的获取部分信息
     *
     * SecurityContextHolder 可以简单理解为一个 ThreadLocal，通过最前端的 {@link org.springframework.security.web.context.SecurityContextPersistenceFilter} 过滤器，
     * 在每次请求到达时检查 session 是否有登陆信息，有则放到 SecurityContextHolder 中;
     * 在请求返回时，检查是否存在 SecurityContextHolder，如果存在则放到 session 中。
     */
    @GetMapping("/me")
    public Object getCurrentUser(Authentication authentication,
                                 @AuthenticationPrincipal UserDetails user) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        return user;
    }

    @GetMapping("/jwtInfo")
    public Object jwtInfo(Authentication user, HttpServletRequest request) {
        // 获取 JWT 串
        String token = StringUtils.substringAfter(request.getHeader("Authorization"), "bearer ");

        // 解析 - 通过密钥
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();

        String company = (String) claims.get("name");

        System.out.println(company);

        return user;
    }

    @PostMapping("/register")
    public void register(User user, HttpServletRequest request) {
        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        String userName = user.getUserName();
        // 信息插入到 social 数据库，第一个参数其实是 userID，唯一即可
        // providerSignInUtils.doPostSignUp(userName, new ServletWebRequest(request));
        appSingUpUtils.doPostSignUp(new ServletWebRequest(request), userName);
    }
}
