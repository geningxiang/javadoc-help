package org.genx.javadoc.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:32
 */
public class ClassDocVO extends AbsDocVO{

    /**
     * 类的泛型
     * 例如 Map<K,V>
     */
    private List<TypeVariableVO> typeParameters;


    /**
     * 所有字段
     */
    private List<TypeDoc> fields;

    /**
     * 所有方法
     */
    private List<MethodDocVO> methods;


    /**
     * 是否是可循环的
     * 通过是否实现 java.lang.Iterable 接口来判断
     */
    private boolean iterable = false;

    /**
     * 判断一下大致类型
     */
    private String type;



    public List<TypeVariableVO> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<TypeVariableVO> typeParameters) {
        this.typeParameters = typeParameters;
    }


    public List<TypeDoc> getFields() {
        return fields;
    }

    public void setFields(List<TypeDoc> fields) {
        this.fields = fields;
    }

    public List<MethodDocVO> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDocVO> methods) {
        this.methods = methods;
    }

    public void addMethod(MethodDocVO method) {
        if(this.methods == null){
            this.methods = new ArrayList(16);
        }
        this.methods.add(method);
    }

    public boolean isIterable() {
        return iterable;
    }

    public void setIterable(boolean iterable) {
        this.iterable = iterable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
