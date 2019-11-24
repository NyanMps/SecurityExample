package com.bfchengnuo.security.demo.domain;

import com.bfchengnuo.security.demo.dto.ResourceInfo;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
@Data
public class Resource {
    /**
     * 数据库表主键
     */
    private Long id;
    /**
     * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
     */
    private Date createdTime;
    /**
     * 资源名称，如xx菜单，xx按钮
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
     * 实际需要控制权限的url
     */
    private Set<String> urls;
    /**
     * 父资源
     */
    private Resource parent;
    /**
     * 子资源
     */
    private List<Resource> childs = new ArrayList<>();

    public ResourceInfo toTree(Admin admin) {
        ResourceInfo result = new ResourceInfo();
        BeanUtils.copyProperties(this, result);
        Set<Long> resourceIds = admin.getAllResourceIds();

        List<ResourceInfo> children = new ArrayList<ResourceInfo>();
        for (Resource child : getChilds()) {
            if(StringUtils.equals(admin.getUsername(), "admin") ||
                    resourceIds.contains(child.getId())){
                children.add(child.toTree(admin));
            }
        }
        result.setChildren(children);
        return result;
    }

    public void addChild(Resource child) {
        childs.add(child);
        child.setParent(this);
    }
    /**
     * 序号
     */
    private int sort;
}
