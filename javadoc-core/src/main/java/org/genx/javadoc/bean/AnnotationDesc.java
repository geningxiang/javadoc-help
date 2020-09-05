package org.genx.javadoc.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * TODO 是否需要把 一个注解继承的注解也读出来
 * @author genx
 * @date 2020/9/4 22:03
 */
public class AnnotationDesc implements Serializable {

    private String name;
    /**
     * 注解的类名
     * 例如 org.springframework.web.bind.annotation.RequestMapping
     */
    private String qualifiedName;
    private Map<String, String[]> data;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public Map<String, String[]> getData() {
        return data;
    }

    public void setData(Map<String, String[]> data) {
        this.data = data;
    }
}
