package com.genx.javadoc.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:33
 */
public class AnnotationVO {
    /**
     * 注解的类名
     * 例如 org.springframework.web.bind.annotation.RequestMapping
     */
    private String className;
    private Map<String, String[]> data = new HashMap(8);

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String[]> getData() {
        return data;
    }

    public String[] getValue(String key) {
        return data != null ? data.get(key) : null;
    }

    public void setValue(String key, String[] value) {
        data.put(key, value);
    }


    public void setData(Map<String, String[]> data) {
        this.data = data;
    }
}
