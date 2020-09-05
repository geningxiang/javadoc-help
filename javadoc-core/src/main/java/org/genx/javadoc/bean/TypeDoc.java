package org.genx.javadoc.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/23 16:44
 */

public class TypeDoc extends AbsDoc {


    /**
     * 名称
     */
    private String name;

    /**
     * 维度
     * 例如 方法返回 String[]
     */
    private int dimension = 1;

    /**
     * 指定的泛型
     */
    private TypeParameterizedDoc[] parameters;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public TypeParameterizedDoc[] getParameters() {
        return parameters;
    }

    public void setParameters(TypeParameterizedDoc[] parameters) {
        this.parameters = parameters;
    }

    /**
     * 用于将 字段中的类型 克隆到 方法
     * @return
     */
    public TypeDoc copy() {
        String str = JSONObject.toJSONString(this);
        return JSONObject.parseObject(str, TypeDoc.class);
    }

    public static TypeDoc ofVoid() {
        TypeDoc typeDoc = new TypeDoc();
        typeDoc.setName("");
        typeDoc.setDimension(1);
        typeDoc.setClassName("void");
        typeDoc.setClassInfo("void");
        return typeDoc;
    }
}


