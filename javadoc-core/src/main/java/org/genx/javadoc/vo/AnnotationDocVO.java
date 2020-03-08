package org.genx.javadoc.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:33
 */
public class AnnotationDocVO {
    private String name;
    private String text;
    /**
     * 注解的类名
     * 例如 org.springframework.web.bind.annotation.RequestMapping
     */
    private String className;
    private Map<String, String[]> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String[]> getData() {
        return data;
    }

    public void setData(Map<String, String[]> data) {
        this.data = data;
    }


    public void setValue(String key, String... value) {
        if(this.data == null){
            this.data = new HashMap(8);
        }
        this.data.put(key, value);
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


}
