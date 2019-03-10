package com.genx.javadoc.reader;

import com.sun.javadoc.AnnotationDesc;

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
                System.out.println(array[i].getClass());
                System.out.println(array[i]);
                if(array[i] instanceof String){
                    ss[i] = ss[i].substring(1, ss[i].length() - 1);
                }
            }
            data.put(elementValuePair.element().name(), ss);
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
