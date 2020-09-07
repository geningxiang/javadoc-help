package org.genx.javadoc.bean;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/4 21:36
 */
public class MethodDoc extends AbsDoc{

    /**
     * 方法名
     */
    private String methodName;


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

    public List<TypeDoc> getParams() {
        return params;
    }

    public void setParams(List<TypeDoc> params) {
        this.params = params;
    }

    public boolean hasNoParam() {
        return this.params == null || this.params.size() == 0;
    }

    public TypeDoc getReturnType() {
        return returnType;
    }

    public void setReturnType(TypeDoc returnType) {
        this.returnType = returnType;
    }

    public Map<String, String> getThrowExpections() {
        return throwExpections;
    }

    public void setThrowExpections(Map<String, String> throwExpections) {
        this.throwExpections = throwExpections;
    }


    public CommentDoc getReturnComment() {
        return getTag("return");
    }


    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(256);
        text.append(this.methodName);
        text.append("(");
        if (this.params != null) {
            for (int i = 0; i < this.params.size(); i++) {
                if (i > 0) {
                    text.append(",");
                }
                text.append(this.params.get(i).getClassName());
                //维度  如果是数组的话
                for (int j = 1; j < this.params.get(i).getDimension(); j++) {
                    text.append("[]");
                }
            }
        }
        text.append(")");
        return text.toString();
    }

    @Override
    public AnnotationDesc getAnnotation(String annotationClassName) {
        if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
            return this.annotations.get(annotationClassName);
        }
        return null;
    }

    @Override
    public boolean hasAnnotation(String annotationClassName) {
        return StringUtils.isNotBlank(annotationClassName) && annotations != null && annotations.containsKey(annotationClassName);
    }

    @Override
    public String getAnnotationValue(String annotationClassName, String key) {
        if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
            AnnotationDesc annotationDocVO = this.annotations.get(annotationClassName);
            return annotationDocVO.getValue(key);
        }
        return null;
    }

    @Override
    public String[] getAnnotationValues(String annotationClassName, String key) {
        if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
            AnnotationDesc annotationDocVO = this.annotations.get(annotationClassName);
            return annotationDocVO.getValues(key);
        }
        return null;
    }

}
