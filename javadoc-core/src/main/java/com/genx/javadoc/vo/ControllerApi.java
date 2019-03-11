package com.genx.javadoc.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 0:24
 */
public class ControllerApi {

    private RequestMapping requestMapping;
    private String className;

    private String methodName;


    private String comment;

    private List<Param> params;


    private String returnType;

    private String returnComment;

    private Map<String, String> throwExpections = new HashMap();

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnComment() {
        return returnComment;
    }

    public void setReturnComment(String returnComment) {
        this.returnComment = returnComment;
    }


    public Map<String, String> getThrowExpections() {
        return throwExpections;
    }

    public void putThrowExpections(String expection, String comment) {
        this.throwExpections.put(expection, comment);
    }

    public void setThrowExpections(Map<String, String> throwExpections) {
        this.throwExpections = throwExpections;
    }

}
