package com.genx.javadoc.utils;

import com.genx.javadoc.reader.AnnotationDescReader;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 19:37
 */
public class SpringMvcUtil {
    private static final String REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";

    public static String getApiUrl(ClassDoc classDoc, MethodDoc methodDoc) {
        AnnotationDesc annotationDesc = JavaDocUtil.getAnnotation(classDoc, REQUEST_MAPPING);
        StringBuilder url = new StringBuilder(32);
        if (annotationDesc != null) {
            AnnotationDescReader r = new AnnotationDescReader(annotationDesc);
            url.append(r.getValue("value"));
        }

        annotationDesc = JavaDocUtil.getAnnotation(methodDoc, REQUEST_MAPPING);
        if (annotationDesc != null) {
            AnnotationDescReader r = new AnnotationDescReader(annotationDesc);
            url.append(r.getValue("value"));
        }

        return url.toString();
    }
}
