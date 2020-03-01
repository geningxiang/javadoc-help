package com.genx.javadoc.vo;

import com.genx.javadoc.utils.AnnotationUtil;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:32
 */
public class ClassDocVO implements IHasAnnotation{

    /**
     * 类名  全名
     * 例如 com.genx.javadoc.vo.ClassDocVO
     */
    private String className;

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
     * 注释 带换行符的
     */
    private String rawCommentText;

    /**
     * 注解
     * key: 注解的类名 例如 org.springframework.web.bind.annotation.RequestMapping
     */
    private Map<String, AnnotationVO> annotations;

    /**
     * 所有字段
     */
    private List fields;

    /**
     * 所有方法
     */
    private List<MethodDocVO> methods;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

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

    public String getRawCommentText() {
        return rawCommentText;
    }

    public void setRawCommentText(String rawCommentText) {
        this.rawCommentText = rawCommentText;
    }

    public Map<String, AnnotationVO> getAnnotations() {
        return annotations;
    }

    public boolean hasAnnotation(String annotationClassName){
        return StringUtils.isNotBlank(annotationClassName) && annotations != null && annotations.containsKey(annotationClassName);
    }

    public AnnotationVO getAnnotation(String annotationClassName) {
        return annotations != null ? annotations.get(annotationClassName) : null;
    }

    public void setAnnotations(Map<String, AnnotationVO> annotations) {
        this.annotations = annotations;
    }

    public List getFields() {
        return fields;
    }

    public void setFields(List fields) {
        this.fields = fields;
    }

    public List<MethodDocVO> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDocVO> methods) {
        this.methods = methods;
    }
}
