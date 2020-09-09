package org.genx.javadoc.bean.rest;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/5 22:18
 */
public class RestPropertiesDoc {

    /**
     * 大致的类型判断
     * @see org.genx.javadoc.contants.RoughlyType
     */
    private int type;

    /**
     * 类名(带泛型)
     */
    protected String classInfo;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 维度
     * 例如 String[] 就是 2
     */
    private int dimension = 1;

    /**
     * 限制
     * 例如 required notNull email 等 一般是JSR-303 读出来的
     */
    private Set<String> limits;


    /**
     * 是否可循环
     */
    private boolean iterable = false;

    /**
     * 下一级
     */
    private List<RestPropertiesDoc> data;


}
