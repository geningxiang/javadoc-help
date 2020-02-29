package com.genx.javadoc.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.genx.javadoc.utils.AnnotationUtil;
import com.sun.javadoc.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:35
 */
public class MethodDocVO {

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 修饰符数值
     */
    private int modifierSpecifier;

    /**
     * 参数
     */
    private List<ParamerVO> params;

    /**
     * 注解
     */
    private Map<String, AnnotationVO> annotations;

    /**
     * 注释
     */
    private String commentText;


    /**
     * 返回类型
     */
    private ReturnTypeVO returnType;


    /**
     * return 的注释
     * 这部分注释暂时没有放到 returnType里面去
     */
    private String returnComment;

    /**
     * 抛出的异常
     */
    private Map<String, String> throwExpections;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getModifierSpecifier() {
        return modifierSpecifier;
    }

    public void setModifierSpecifier(int modifierSpecifier) {
        this.modifierSpecifier = modifierSpecifier;
    }

    public List<ParamerVO> getParams() {
        return params;
    }

    public void setParams(List<ParamerVO> params) {
        this.params = params;
    }

    public Map<String, AnnotationVO> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Map<String, AnnotationVO> annotations) {
        this.annotations = annotations;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public ReturnTypeVO getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnTypeVO returnType) {
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

    public void setThrowExpections(Map<String, String> throwExpections) {
        this.throwExpections = throwExpections;
    }
}
