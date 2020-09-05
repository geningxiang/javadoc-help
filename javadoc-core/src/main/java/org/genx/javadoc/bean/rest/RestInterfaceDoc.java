package org.genx.javadoc.bean.rest;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/5 21:57
 */
public class RestInterfaceDoc {

    /**
     * 接口地址
     * 不包含 baseUrl
     */
    private String url;

    /**
     * 支持的哪些method
     *  POST、GET等
     */
    private String[] methods;

    /**
     * 名称
     * 一般是取  @RequestMapping(name="获取APP首页数据")
     * 或者注释第一行
     */
    private String name;

    /**
     * 获取注释
     */
    private String description;

    private Map<String, String> headers;

    private Map<String, String> parameters;

    private Map<String, String> bodys;

}
