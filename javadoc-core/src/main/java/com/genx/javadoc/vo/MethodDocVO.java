package com.genx.javadoc.vo;

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

    private String methodName;

    private Map<String, AnnotationVO> annotations;

    private String comment;

    private List<ParamerVO> params;

    private String returnType;

    private String returnComment;

    private Map<String, String> throwExpections;

    public MethodDocVO(MethodDoc methodDoc){
        this.methodName = methodDoc.name();
        this.comment = methodDoc.commentText();

        this.annotations = AnnotationUtil.readAnnotationMap(methodDoc);


        List<ParamerVO> params = new ArrayList<>(methodDoc.parameters().length);
        Map<String, String> paramCommentMap = new HashMap();
        for (ParamTag paramTag : methodDoc.paramTags()) {
            paramCommentMap.put(paramTag.parameterName(), paramTag.parameterComment());
        }

        for (Parameter parameter : methodDoc.parameters()) {
            params.add(new ParamerVO(parameter, paramCommentMap.get(parameter.name())));
        }
        this.params = params;

        this.returnType = methodDoc.returnType().toString();

        Tag[] returnTags = methodDoc.tags("return");
        if(returnTags.length > 0){
           this.returnComment = returnTags[0].text();
        }

        throwExpections = new HashMap(8);
        for (ThrowsTag throwsTag : methodDoc.throwsTags()) {
            throwExpections.put(throwsTag.exceptionName(), throwsTag.exceptionComment());
        }

    }


    public String getMethodName() {
        return methodName;
    }

    public String getComment() {
        return comment;
    }

    public List<ParamerVO> getParams() {
        return params;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getReturnComment() {
        return returnComment;
    }

    public Map<String, String> getThrowExpections() {
        return throwExpections;
    }

    public Map<String, AnnotationVO> getAnnotations() {
        return annotations;
    }
}
