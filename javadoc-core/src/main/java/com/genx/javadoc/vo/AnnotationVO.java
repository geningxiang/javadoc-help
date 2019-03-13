package com.genx.javadoc.vo;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:33
 */
public class AnnotationVO {
//    private String className;
    private Map<String, String[]> data;

//    public String getClassName() {
//        return className;
//    }

//    public void setClassName(String className) {
//        this.className = className;
//    }

    public Map<String, String[]> getData() {
        return data;
    }

    public void setData(Map<String, String[]> data) {
        this.data = data;
    }
}
