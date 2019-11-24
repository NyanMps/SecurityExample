package com.bfchengnuo.security.demo.dto;

import com.bfchengnuo.security.demo.domain.ResourceType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
@Data
public class ResourceInfo {
    /**
     * 资源ID
     */
    private Long id;
    /**
     * 父ID
     */
    private Long parentId;
    /**
     * 资源名
     */
    private String name;
    /**
     * 资源链接
     */
    private String link;
    /**
     * 图标
     */
    private String icon;
    /**
     * 资源类型
     */
    private ResourceType type;
    /**
     * 子节点
     */
    private List<ResourceInfo> children = new ArrayList<>();
}
