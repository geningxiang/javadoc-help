package com.genx.javadoc.reader;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeElementDoc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 19:45
 */
public class AnnotationDescReader {

    private Map<String, String[]> data = new HashMap();

    public AnnotationDescReader(AnnotationDesc annotationDesc){
        for (AnnotationDesc.ElementValuePair elementValuePair : annotationDesc.elementValues()) {
            Object value = elementValuePair.value().value();
            Object[] array;
            if(value.getClass().isArray()){
                array = (Object[]) value;
            } else {
                array = new Object[]{value};
            }
            String[] ss = new String[array.length];
            for (int i = 0; i < array.length; i++) {
                ss[i] = String.valueOf(array[i]);
                if(ss[i].startsWith("\"") && ss[i].endsWith("\"")){
                    ss[i] = ss[i].substring(1, ss[i].length() - 1);
                }
            }
            data.put(elementValuePair.element().name(), ss);
        }

        for (AnnotationTypeElementDoc element : annotationDesc.annotationType().elements()) {
            if(!data.containsKey(element.name()) && element.defaultValue().value() != null){
                Object[] array;
                if(element.defaultValue().value().getClass().isArray()){
                    array = (Object[]) element.defaultValue().value();
                }else {
                    array = new Object[]{element.defaultValue().value()};
                }
                String[] ss = new String[array.length];
                for (int i = 0; i < array.length; i++) {
                    ss[i] = String.valueOf(array[i]);
                    if(ss[i].startsWith("\"") && ss[i].endsWith("\"")){
                        ss[i] = ss[i].substring(1, ss[i].length() - 1);
                    }
                }
                data.put(element.name(), ss);

            }
        }

    }

    public Map<String, String[]> getData() {
        return data;
    }

    public String getValue(String name) {
        String[] ss = data.get(name);
        if(ss != null && ss.length > 0){
            return ss[0];
        }
        return "";
    }

    public String[] getArrayValue(String name) {
        String[] ss = data.get(name);
        if(ss != null && ss.length > 0){
            return ss;
        }
        return new String[0];
    }
}
