package com.genx.javadoc.utils;

import com.genx.javadoc.vo.AnnotationVO;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.ProgramElementDoc;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 10:01
 */
public class AnnotationUtil {

    public static Map<String, AnnotationVO> readAnnotationMap(ProgramElementDoc doc){
        return readAnnotationMap(doc.annotations());
    }

    public static Map<String, AnnotationVO> readAnnotationMap(AnnotationDesc[] annotationDescs){
        Map<String, AnnotationVO> map = new HashMap<>(8);
        for (AnnotationDesc annotation : annotationDescs) {
            AnnotationVO annotationVO = new AnnotationVO();
            annotationVO.setClassName(annotation.annotationType().qualifiedTypeName());

            Map<String, String[]> data = new HashMap<>(8);
            for (AnnotationDesc.ElementValuePair elementValuePair : annotation.elementValues()) {
                data.put(elementValuePair.element().name(), parseObjectToStringArray(elementValuePair.value().value()));
            }
            //设置默认值
            for (AnnotationTypeElementDoc element : annotation.annotationType().elements()) {
                if(!data.containsKey(element.name()) && element.defaultValue() != null &&  element.defaultValue().value() != null){
                    data.put(element.name(), parseObjectToStringArray(element.defaultValue().value()));
                }
            }
            annotationVO.setData(data);
            map.put(annotation.annotationType().qualifiedTypeName(), annotationVO);
        }
        return map;
    }

    public static String[] readAnnotationValue(ProgramElementDoc doc, String annotationClassName, String key){
        Map<String, AnnotationVO> map = readAnnotationMap(doc);
        AnnotationVO annotationVO = map.get(annotationClassName);
        if(annotationVO != null){
            return annotationVO.getValue(key);
        }
        return null;
    }


    private static String[] parseObjectToStringArray(Object value){
        Object[] array;
        if(value.getClass().isArray()){
            array = (Object[]) value;
            if(array.length == 0){
                return null;
            }
        } else {
            if(String.valueOf(value).length() == 0){
                return null;
            }
            array = new Object[]{value};
        }
        String[] ss = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ss[i] = String.valueOf(array[i]);
            if(ss[i].startsWith("\"") && ss[i].endsWith("\"")){
                ss[i] = ss[i].substring(1, ss[i].length() - 1);
            }
        }
        return ss;
    }

    public static boolean hasAnnotation(ProgramElementDoc doc, String annotationClassName){
        if(doc != null && StringUtils.isNotBlank(annotationClassName)) {
            for (AnnotationDesc annotation : doc.annotations()) {
                if (annotationClassName.equals(annotation.annotationType().qualifiedTypeName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
