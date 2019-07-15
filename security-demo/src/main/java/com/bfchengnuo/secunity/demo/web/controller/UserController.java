package com.bfchengnuo.secunity.demo.web.controller;

import com.bfchengnuo.secunity.demo.dto.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/user")
public class UserController {
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
        return new User("1", "name".concat(id), "pwd");
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
}
