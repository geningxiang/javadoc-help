package org.genx.javadoc.bean.rest;

import org.genx.javadoc.bean.CommentDoc;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

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


    private String[] urls;

    /**
     * 支持的哪些method
     *  POST、GET等
     */
    private String[] methods;

    /**
     * 返回的数据类型
     * 例如 application/json  charset=UTF-8
     */
    private String produces;

    /**
     * 名称
     * 一般是取  @RequestMapping(name="获取APP首页数据")
     * 或者注释第一行
     */
    private String name;

    /**
     * 获取注释
     */
    private CommentDoc description;

    /**
     * 请求地址上的参数
     * 例如 @PathVariable("id")
     */
    private Map<String, RestTypeDoc> pathVariables = new LinkedHashMap();

    /**
     * header参数
     */
    private Map<String, RestTypeDoc> headers = new TreeMap();

    /**
     * param参数
     */
    private Map<String, RestTypeDoc> params = new TreeMap();

    /**
     * body参数
     */
    private Map<String, RestNestTypeDoc> bodys = new TreeMap();

    /**
     * 返回的
     */
    private RestNestTypeDoc returnBody;

    /**
     * 返回的备注
     */
    private CommentDoc returnComment;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }

    public String getProduces() {
        return produces;
    }

    public void setProduces(String produces) {
        this.produces = produces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommentDoc getDescription() {
        return description;
    }

    public void setDescription(CommentDoc description) {
        this.description = description;
    }

    public Map<String, RestTypeDoc> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, RestTypeDoc> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public void addPathVariable(String key, RestTypeDoc pathVariable) {
        this.pathVariables.put(key, pathVariable);
    }

    public Map<String, RestTypeDoc> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, RestTypeDoc> headers) {
        if (headers != null) {
            this.headers.putAll(headers);
        }
    }

    public void addHeader(String key, RestTypeDoc restTypeDoc) {
        this.headers.put(key, restTypeDoc);

    }

    public Map<String, RestTypeDoc> getParams() {
        return params;
    }

    public void setParams(Map<String, RestTypeDoc> params) {
        if (params != null) {
            this.params.putAll(params);
        }
    }

    public void addParam(String key, RestTypeDoc param) {
        this.params.put(key, param);
    }

    public Map<String, RestNestTypeDoc> getBodys() {
        return bodys;
    }

    public void setBodys(Map<String, RestNestTypeDoc> bodys) {
        if (bodys != null) {
            this.bodys.putAll(bodys);
        }
    }

    public void addBody(String key, RestNestTypeDoc body) {
        this.bodys.put(key, body);
    }

    public RestNestTypeDoc getReturnBody() {
        return returnBody;
    }

    public void setReturnBody(RestNestTypeDoc returnBody) {
        this.returnBody = returnBody;
    }

    public CommentDoc getReturnComment() {
        return returnComment;
    }

    public void setReturnComment(CommentDoc returnComment) {
        this.returnComment = returnComment;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder(64);
        content.append(this.url);
        if (this.methods != null && this.methods.length > 0) {
            content.append("@").append(this.methods[0]);
        }
        return content.toString();
    }
}
