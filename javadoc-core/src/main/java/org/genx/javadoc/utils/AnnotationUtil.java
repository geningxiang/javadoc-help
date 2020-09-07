package org.genx.javadoc.utils;

import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.ProgramElementDoc;
import org.genx.javadoc.bean.AnnotationDesc;

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

    public static Map<String, AnnotationDesc> readAnnotationMap(ProgramElementDoc doc) {
        return readAnnotationMap(doc.annotations());
    }

    public static Map<String, AnnotationDesc> readAnnotationMap(com.sun.javadoc.AnnotationDesc[] annotationDescs) {
        if (annotationDescs == null || annotationDescs.length == 0) {
            return null;
        }
        Map<String, AnnotationDesc> map = new HashMap<>(8);
        for (com.sun.javadoc.AnnotationDesc annotation : annotationDescs) {
            AnnotationDesc annotationVO = new AnnotationDesc();
            annotationVO.setName(annotation.annotationType().name());
            annotationVO.setText(annotation.toString());
            annotationVO.setQualifiedName(annotation.annotationType().qualifiedTypeName());
            Map<String, String[]> data = new HashMap<>(8);
            for (com.sun.javadoc.AnnotationDesc.ElementValuePair elementValuePair : annotation.elementValues()) {
                data.put(elementValuePair.element().name(), parseObjectToStringArray(elementValuePair.value().value()));
            }
            //设置默认值
            for (AnnotationTypeElementDoc element : annotation.annotationType().elements()) {
                if (!data.containsKey(element.name()) && element.defaultValue() != null && element.defaultValue().value() != null) {
                    data.put(element.name(), parseObjectToStringArray(element.defaultValue().value()));
                }
            }
            annotationVO.setData(data);
            map.put(annotation.annotationType().qualifiedTypeName(), annotationVO);
        }
        return map;
    }

    private static String[] parseObjectToStringArray(Object value) {
        Object[] array;
        if (value.getClass().isArray()) {
            array = (Object[]) value;
            if (array.length == 0) {
                return null;
            }
        } else {
            if (String.valueOf(value).length() == 0) {
                return null;
            }
            array = new Object[]{value};
        }
        String[] ss = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ss[i] = String.valueOf(array[i]);
            if (ss[i].startsWith("\"") && ss[i].endsWith("\"")) {
                ss[i] = ss[i].substring(1, ss[i].length() - 1);
            }
        }
        return ss;
    }

}
