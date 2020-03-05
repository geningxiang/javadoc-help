package org.genx.javadoc.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:35
 */
public class MethodDocVO extends AbsDocVO {

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 修饰符数值
     */
    private int modifierSpecifier;

    /**
     * 参数
     */
    private List<TypeDoc> params;


    /**
     * 返回类型
     */
    private TypeDoc returnType;


    /**
     * 抛出的异常
     */
    private Map<String, String> throwExpections;


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getModifierSpecifier() {
        return modifierSpecifier;
    }

    public void setModifierSpecifier(int modifierSpecifier) {
        this.modifierSpecifier = modifierSpecifier;
    }

    public List<TypeDoc> getParams() {
        return params;
    }

    public void setParams(List<TypeDoc> params) {
        this.params = params;
    }

    public TypeDoc getReturnType() {
        return returnType;
    }

    public void setReturnType(TypeDoc returnType) {
        this.returnType = returnType;
    }

    public String getReturnComment() {
        return StringUtils.trimToEmpty(getTag("return"));
    }


    public Map<String, String> getThrowExpections() {
        return throwExpections;
    }

    public void setThrowExpections(Map<String, String> throwExpections) {
        this.throwExpections = throwExpections;
    }
}
