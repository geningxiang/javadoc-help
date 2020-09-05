package org.genx.javadoc.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description: 泛型
 * @author genx
 * @date 2020/2/15 16:32
 */
public class TypeVariableDoc implements Serializable {

    /**
     * 泛型名称
     * K
     */
    private String name;

    /**
     * 描述
     * 例如: K extends java.io.Serializable
     */
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
