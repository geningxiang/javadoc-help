package com.genx.javadoc.vo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 0:25
 */
public class Param {

    private String className;

    private String name;

    private String comment;

    public Param(String className, String name, String comment) {
        this.className = className;
        this.name = name;
        this.comment = comment;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
