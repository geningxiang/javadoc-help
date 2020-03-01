package com.genx.javadoc.vo;

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
public class MethodDocVO implements IHasAnnotation{

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
     * 注解
     */
    private Map<String, AnnotationVO> annotations;

    /**
     * 注释
     */
    private String commentText;


    /**
     * 返回类型
     */
    private TypeDoc returnType;


    /**
     * return 的注释
     * 这部分注释暂时没有放到 returnType里面去
     */
    private String returnComment;

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

    public Map<String, AnnotationVO> getAnnotations() {
        return annotations;
    }
    public AnnotationVO getAnnotation(String annotationClassName) {
        return annotations != null ? annotations.get(annotationClassName) : null;
    }


    public boolean hasAnnotation(String annotationClassName){
        return StringUtils.isNotBlank(annotationClassName) && annotations != null && annotations.containsKey(annotationClassName);
    }

    public void setAnnotations(Map<String, AnnotationVO> annotations) {
        this.annotations = annotations;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public TypeDoc getReturnType() {
        return returnType;
    }

    public void setReturnType(TypeDoc returnType) {
        this.returnType = returnType;
    }

    public String getReturnComment() {
        return returnComment;
    }

    public void setReturnComment(String returnComment) {
        this.returnComment = returnComment;
    }

    public Map<String, String> getThrowExpections() {
        return throwExpections;
    }

    public void setThrowExpections(Map<String, String> throwExpections) {
        this.throwExpections = throwExpections;
    }
}
