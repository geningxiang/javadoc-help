package org.genx.javadoc.bean.rest;

import com.alibaba.fastjson.JSON;
import org.genx.javadoc.bean.TypeDoc;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Description: 详细的类型， 将遍历子集 构成一棵树
 * @author genx
 * @date 2020/3/8 14:38
 */
public class RestTypeDoc extends TypeDoc {

    /**
     * 大致的类型判断
     * @see org.genx.javadoc.contants.RoughlyType
     */
    private String type;




    public RestTypeDoc() {

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
