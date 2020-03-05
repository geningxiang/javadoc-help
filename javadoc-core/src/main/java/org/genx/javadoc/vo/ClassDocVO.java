package org.genx.javadoc.vo;

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
     * 修饰符数值
     * {@link java.lang.reflect.Modifier}
     */
    private int modifierSpecifier;

    /**
     * 类的泛型
     * 例如 Map<K,V>
     */
    private List<TypeVariableVO> typeParameters;


    /**
     * 所有字段
     */
    private List<FieldDocVO> fields;

    /**
     * 所有方法
     */
    private List<MethodDocVO> methods;



    public int getModifierSpecifier() {
        return modifierSpecifier;
    }

    public void setModifierSpecifier(int modifierSpecifier) {
        this.modifierSpecifier = modifierSpecifier;
    }

    public List<TypeVariableVO> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<TypeVariableVO> typeParameters) {
        this.typeParameters = typeParameters;
    }



    public List<FieldDocVO> getFields() {
        return fields;
    }

    public void setFields(List<FieldDocVO> fields) {
        this.fields = fields;
    }

    public List<MethodDocVO> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDocVO> methods) {
        this.methods = methods;
    }
}
