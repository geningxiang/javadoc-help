package org.genx.javadoc.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/5 21:23
 */
public abstract class AbsDocVO {


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
    private int modifierSpecifier;

    /**
     * 注解
     * key 注解的类名
     */
    protected Map<String, AnnotationDocVO> annotations;

    /**
     * 注释 带换行符的
     */
    protected String comment;

    /**
     * 注释标签
     * 例如 @ param
     */
    protected Map<String, String> tags;



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

    public Map<String, AnnotationDocVO> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Map<String, AnnotationDocVO> annotations) {
        this.annotations = annotations;
    }

    public boolean hasAnnotation(String annotationClassName) {
        return StringUtils.isNotBlank(annotationClassName) && annotations != null && annotations.containsKey(annotationClassName);
    }

    public AnnotationDocVO getAnnotation(String annotationClassName) {
        if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
            return this.annotations.get(annotationClassName);
        }
        return null;
    }

    public String getAnnotationValue(String annotationClassName, String key) {
        if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
            AnnotationDocVO annotationDocVO = this.annotations.get(annotationClassName);
            return annotationDocVO.getValue(key);
        }
        return null;
    }

    public String[] getAnnotationValues(String annotationClassName, String key) {
        if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
            AnnotationDocVO annotationDocVO = this.annotations.get(annotationClassName);
            return annotationDocVO.getValues(key);
        }
        return null;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public String getTag(String tagKey) {
        if(StringUtils.isBlank(tagKey) || this.tags == null){
            return "";
        }
        if (tagKey.charAt(0) != '@') {
            tagKey = "@" + tagKey;
        }
        return StringUtils.trimToEmpty(this.tags.get(tagKey));
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public int getModifierSpecifier() {
        return modifierSpecifier;
    }

    public void setModifierSpecifier(int modifierSpecifier) {
        this.modifierSpecifier = modifierSpecifier;
    }
}
