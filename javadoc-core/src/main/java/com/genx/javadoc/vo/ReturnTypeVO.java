package com.genx.javadoc.vo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/23 16:44
 */

public class ReturnTypeVO {

    /**
     * 类名(全)
     * com.genx.javadoc.entity.ResponseBody
     */
    private String className;

    /**
     * 带泛型的类名
     * com.genx.javadoc.entity.ResponseBody<java.util.List<com.genx.javadoc.entity.User>>
     */
    private String classInfo;

    /**
     * 名称
     * 第一级是没有名称的
     */
    private String name;

    /**
     * 注释
     * 第一级是没有注释的， 方法的注释现在只显示在 {@link MethodDocVO#getReturnComment()}
     */
    private String comment;

    /**
     * 维度
     * 例如 方法返回 String[]
     */
    private int dimension = 0;

    /**
     * 是否可循环 是否是list set 等
     * 通过是否实现 java.lang.Iterable 接口来判断
     */
    private boolean iterable = false;

    private List<ReturnTypeVO> data;


    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public boolean isIterable() {
        return iterable;
    }

    public void setIterable(boolean iterable) {
        this.iterable = iterable;
    }


    public List<ReturnTypeVO> getData() {
        return data;
    }

    public void setData(List<ReturnTypeVO> data) {
        this.data = data;
    }
}


