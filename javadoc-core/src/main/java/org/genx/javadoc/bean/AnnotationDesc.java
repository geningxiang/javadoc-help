package org.genx.javadoc.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
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

    private String text;

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

    public String getValue(String key) {
        String[] ss = getValues(key);
        return ss != null && ss.length > 0 ? ss[0] : null;
    }

    public String[] getValues(String key) {
        if (this.data != null && StringUtils.isNotBlank(key)) {
            String[] values = this.data.get(key);
            if (values != null) {
                return values;
            }
        }
        return null;
    }

    public void setValue(String key, String... value) {
        if(this.data == null){
            this.data = new HashMap(8);
        }
        this.data.put(key, value);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
