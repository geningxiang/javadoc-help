package org.genx.javadoc.bean;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/4 21:36
 */
public class MethodDoc implements Serializable {

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


    /**
     * 修饰符数值
     */
    private int modifierSpecifier;

    /**
     * 注解
     * key 注解的类名
     */
    private Map<String, AnnotationDesc> annotations;

    /**
     * 注释 带换行符的
     */
    private CommentDoc comment;

    /**
     * 注释标签
     * 例如 @ param
     */
    private Map<String, CommentDoc> tags;

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

    public int getModifierSpecifier() {
        return modifierSpecifier;
    }

    public void setModifierSpecifier(int modifierSpecifier) {
        this.modifierSpecifier = modifierSpecifier;
    }

    public Map<String, AnnotationDesc> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Map<String, AnnotationDesc> annotations) {
        this.annotations = annotations;
    }

    public CommentDoc getComment() {
        return comment;
    }

    public void setComment(CommentDoc comment) {
        this.comment = comment;
    }

    public Map<String, CommentDoc> getTags() {
        return tags;
    }

    public void setTags(Map<String, CommentDoc> tags) {
        this.tags = tags;
    }
}
