package org.genx.javadoc.bean.rest;

import com.alibaba.fastjson.JSON;
import org.genx.javadoc.bean.TypeDoc;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Description: 可迭代的类型
 * @author genx
 * @date 2020/9/7 22:20
 */
public class RestNestTypeDoc extends RestTypeDoc {

    /**
     * 是否可循环
     */
    private boolean iterable = false;

    /**
     * 子集
     */
    private Collection<RestTypeDoc> data;

    public boolean isIterable() {
        return iterable;
    }

    public void setIterable(boolean iterable) {
        this.iterable = iterable;
    }

    public Collection<RestTypeDoc> getData() {
        return data;
    }

    public void setData(Collection<RestTypeDoc> data) {
        this.data = data;
    }


    public static RestNestTypeDoc copyFromTypeDoc(TypeDoc typeDoc) {
        String s = JSON.toJSONString(typeDoc);
        return JSON.parseObject(s, RestNestTypeDoc.class);
    }

}
