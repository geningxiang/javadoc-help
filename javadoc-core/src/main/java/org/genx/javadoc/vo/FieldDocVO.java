package org.genx.javadoc.vo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2020/2/15 16:46
 */
public class FieldDocVO extends AbsDocVO{

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 修饰符数值
     */
    private int modifierSpecifier;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getModifierSpecifier() {
        return modifierSpecifier;
    }

    public void setModifierSpecifier(int modifierSpecifier) {
        this.modifierSpecifier = modifierSpecifier;
    }

}
