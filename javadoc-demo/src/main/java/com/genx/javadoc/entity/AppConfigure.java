package com.genx.javadoc.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/1 13:33
 */
public class AppConfigure {

    /**
     * ID
     */
    private Integer id;

    /**
     * 上级ID
     */
    private Integer pid;

    /**
     * 路径
     */
    private Integer location;

    /**
     * 唯一key
     */
    private String key;

    /**
     * 类型
     */
    private String type;

    /**
     * 类型
     */
    private String name;

    /**
     * 图标
     */
    private String iconPath;

    /**
     * 动作
     */
    private String action;

    /**
     * 备注
     */
    private String remark;

    /**
     * 附加属性
     */
    private String attributes;

    /**
     * 权限配置
     */
    private String ruleData;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序值
     */
    private Integer orderNum;

    /**
     * 状态
     * 1正常 0删除
     */
    private Integer status;


    private List<AppConfigure> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getRuleData() {
        return ruleData;
    }

    public void setRuleData(String ruleData) {
        this.ruleData = ruleData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<AppConfigure> getChildren() {
        return children;
    }

    public void setChildren(List<AppConfigure> children) {
        this.children = children;
    }
}
