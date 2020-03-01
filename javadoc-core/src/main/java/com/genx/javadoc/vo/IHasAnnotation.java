package com.genx.javadoc.vo;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/2 0:26
 */
public interface IHasAnnotation {

    Map<String, AnnotationVO> getAnnotations();

    boolean hasAnnotation(String annotationClassName);

    AnnotationVO getAnnotation(String annotationClassName);

    void setAnnotations(Map<String, AnnotationVO> annotations);


}
