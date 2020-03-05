package org.genx.javadoc.vo;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/23 16:44
 */

public class TypeDoc extends AbsDocVO{


    /**
     * 名称
     * 第一级是没有名称的
     */
    private String name;

    /**
     * 维度
     * 例如 方法返回 String[]
     */
    private int dimension = 1;

    /**
     * 是否可循环 是否是list set 等
     * 通过是否实现 java.lang.Iterable 接口来判断
     */
    private boolean iterable = false;

    private Collection<TypeDoc> data;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public Collection<TypeDoc> getData() {
        return data;
    }

    public void setData(Collection<TypeDoc> data) {
        this.data = data;
    }



    public static TypeDoc ofObject(String classInfo, String name, String comment){
        TypeDoc typeDoc = new TypeDoc();
        typeDoc.setClassInfo(classInfo);
        typeDoc.setClassName(Object.class.getName());
        typeDoc.setName(name);
        typeDoc.setComment(comment);
        return typeDoc;

    }
}


