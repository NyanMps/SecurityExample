package com.bfchengnuo.secunity.demo.web.controller;

import com.bfchengnuo.secunity.demo.dto.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by 冰封承諾Andy on 2019/7/8.
 */
@RestController
public class UserController {
    @GetMapping("/user")
    public List<User> query(@PageableDefault(size = 10, page = 1) Pageable pageable) {
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());

        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }

    @GetMapping("/get/{id::\\d+}")
    public User getInfo(@PathVariable String id) {
        return new User("name".concat(id), "pwd");
    }
}
