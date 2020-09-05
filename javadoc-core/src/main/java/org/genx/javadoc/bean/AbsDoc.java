package org.genx.javadoc.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/5 9:01
 */
public abstract class AbsDoc implements Serializable {

    /**
     * 类名
     */
    protected String className;

    /**
     * 类名(带泛型)
     */
    protected String classInfo;

    /**
     * 修饰符数值
     */
    protected int modifierSpecifier;

    /**
     * 注解
     * key 注解的类名
     */
    protected Map<String, AnnotationDesc> annotations;

    /**
     * 注释
     */
    protected CommentDoc comment;

    /**
     * 注释标签
     * 例如 @ param
     */
    protected Map<String, CommentDoc> tags;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
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
