package com.bfchengnuo.security.demo.domain;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
@Data
public class Role {
    /**
     * 数据库表主键
     */
    private Long id;
    /**
     * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
     */
    private Date createdTime;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色拥有权限的资源集合
     */
    private Set<RoleResource> resources  = new HashSet<>();
    /**
     * 角色的用户集合
     */
    private Set<RoleAdmin> admins = new HashSet<>();
}
