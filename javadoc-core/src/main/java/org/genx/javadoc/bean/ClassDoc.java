package org.genx.javadoc.bean;

import org.genx.javadoc.vo.TypeVariableVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/4 21:31
 */
public class ClassDoc extends AbsDoc {

    /**
     * 类的泛型
     * 例如 Map<K,V>
     */
    private List<TypeVariableDoc> typeParameters;

    /**
     * 变量
     */
    private List<TypeDoc> fields;

    /**
     * 方法
     */
    private List<MethodDoc> methods;

    public List<TypeVariableDoc> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<TypeVariableDoc> typeParameters) {
        this.typeParameters = typeParameters;
    }

    public List<TypeDoc> getFields() {
        return fields;
    }

    public void setFields(List<TypeDoc> fields) {
        this.fields = fields;
    }

    public List<MethodDoc> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDoc> methods) {
        this.methods = methods;
    }
}
