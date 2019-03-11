package com.genx.javadoc.utils;

import com.genx.javadoc.reader.AnnotationDescReader;
import com.genx.javadoc.vo.RequestMapping;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ProgramElementDoc;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 19:37
 */
public class SpringMvcUtil {
    private static final String REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";

    public static RequestMapping readRequestMapping(ClassDoc classDoc, MethodDoc methodDoc) {
        RequestMapping c = readRequestMapping(classDoc);
        RequestMapping m = readRequestMapping(methodDoc);
        if (c != null) {
            if (c.getPaths() != null && c.getPaths().length > 0) {
                String[] paths = m.getPaths();
                for (int i = 0; i < paths.length; i++) {
                    paths[i] = c.getPaths()[0] + paths[i];
                }
                m.setPaths(paths);
            }
            if(m.getMethods().length == 0 && c.getMethods().length > 0){
                m.setMethods(c.getMethods());
            }
            if(m.getProduces().length == 0 && c.getProduces().length > 0){
                m.setProduces(c.getProduces());
            }
        }
        return m;
    }

    private static RequestMapping readRequestMapping(ProgramElementDoc doc) {
        AnnotationDesc annotationDesc = JavaDocUtil.getAnnotation(doc, REQUEST_MAPPING);
        if (annotationDesc == null) {
            return null;
        }
        AnnotationDescReader r = new AnnotationDescReader(annotationDesc);
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setName(r.getValue("name"));
        requestMapping.setPaths(r.getArrayValue("value"));
        String[] methods = r.getArrayValue("method");
        for (int i = 0; i < methods.length; i++) {
            methods[i] = methods[i].replace("org.springframework.web.bind.annotation.RequestMethod.", "");
        }
        requestMapping.setMethods(methods);
        requestMapping.setProduces(r.getArrayValue("produces"));
        return requestMapping;
    }

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
