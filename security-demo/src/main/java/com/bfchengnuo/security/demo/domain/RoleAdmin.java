package com.bfchengnuo.security.demo.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
@Data
public class RoleAdmin {
    /**
     * 数据库表主键
     */
    private Long id;
    /**
     * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
     */
    private Date createdTime;
    /**
     * 角色
     */
    private Role role;
    /**
     * 管理员
     */
    private Admin admin;
}
