package com.genx.javadoc.utils;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ProgramElementDoc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 19:24
 */
public class JavaDocUtil {

    public static boolean hasAnnotation(ProgramElementDoc doc, String annotationName) {
        for (AnnotationDesc annotation : doc.annotations()) {
            if (annotation.annotationType().qualifiedTypeName().equals(annotationName)) {
                return true;
            }
        }
        return false;
    }

    public static AnnotationDesc getAnnotation(ProgramElementDoc doc, String annotationName) {
        for (AnnotationDesc annotation : doc.annotations()) {
            if (annotation.annotationType().qualifiedTypeName().equals(annotationName)) {
                return annotation;
            }
        }
        return null;
    }
}
