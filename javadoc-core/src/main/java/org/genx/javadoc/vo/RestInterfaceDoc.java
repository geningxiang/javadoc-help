package org.genx.javadoc.vo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: http接口文档
 * @author genx
 * @date 2020/3/1 14:37
 */
public class RestInterfaceDoc {

    /**
     * 接口相对地址
     * 如果是多地址时 这个字段取第一个地址
     */
    private String url;

    /**
     * 多地址
     */
    private String[] urls;

    /**
     * 支持的请求类型
     * POST、GET 等
     */
    private String[] methods;

    /**
     * 返回的数据类型
     */
    private String produces;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口的备注
     */
    private String description;

    /**
     * 参数
     */
    private List<TypeDoc> params;

    /**
     * 返回的
     */
    private TypeDoc returnBody;

    /**
     * 返回的备注
     */
    private String returnComment;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TypeDoc> getParams() {
        return params;
    }

    public void setParams(List<TypeDoc> params) {
        this.params = params;
    }

    public TypeDoc getReturnBody() {
        return returnBody;
    }

    public void setReturnBody(TypeDoc returnBody) {
        this.returnBody = returnBody;
    }

    public String getReturnComment() {
        return returnComment;
    }

    public void setReturnComment(String returnComment) {
        this.returnComment = returnComment;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
