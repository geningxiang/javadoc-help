package org.genx.javadoc.vo;

import com.alibaba.fastjson.JSON;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Description: 详细的类型， 将遍历子集 构成一棵树
 * @author genx
 * @date 2020/3/8 14:38
 */
public class DetailedTypeDoc extends TypeDoc {

    /**
     * 大致的类型判断
     * @see org.genx.javadoc.contants.RoughlyType
     */
    private String type;

    /**
     * 是否可循环
     */
    private boolean iterable = false;

    /**
     * 子集
     */
    private Collection<DetailedTypeDoc> data;

    public DetailedTypeDoc() {

    }

    public static DetailedTypeDoc copyFromTypeDoc(TypeDoc typeDoc) {
        String s = JSON.toJSONString(typeDoc);
        return JSON.parseObject(s, DetailedTypeDoc.class);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIterable() {
        return iterable;
    }

    public void setIterable(boolean iterable) {
        this.iterable = iterable;
    }

    public Collection<DetailedTypeDoc> getData() {
        return data;
    }

    public void setData(Collection<DetailedTypeDoc> data) {
        this.data = data;
    }
}
