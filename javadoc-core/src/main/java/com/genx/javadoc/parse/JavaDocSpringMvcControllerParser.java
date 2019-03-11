package com.genx.javadoc.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.genx.javadoc.filter.IJavaDocFilter;
import com.genx.javadoc.utils.SpringMvcUtil;
import com.genx.javadoc.vo.ControllerApi;
import com.genx.javadoc.vo.Param;
import com.genx.javadoc.vo.RequestMapping;
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
 * @date: 2019/3/8 17:16
 */
public class JavaDocSpringMvcControllerParser {

    private final RootDoc rootDoc;

    private final IJavaDocFilter javaDocFilter;

    private JSONObject paths = new JSONObject(256);

    public JavaDocSpringMvcControllerParser(RootDoc rootDoc, IJavaDocFilter javaDocFilter) {
        this.rootDoc = rootDoc;
        this.javaDocFilter = javaDocFilter;

    }

    public void parse() {
        ClassDoc[] classes = rootDoc.classes();

        for (ClassDoc c : classes) {
            if (javaDocFilter.accept(c)) {
                parse(c);
            }
        }
    }

    private void parse(ClassDoc classDoc) {
        for (MethodDoc methodDoc : classDoc.methods(false)) {
            parse(classDoc, methodDoc);
        }
    }

    private void parse(ClassDoc classDoc, MethodDoc methodDoc) {

        ControllerApi api = new ControllerApi();
        api.setClassName(classDoc.qualifiedTypeName());
        api.setMethodName(methodDoc.name());
        api.setComment(methodDoc.commentText());

        RequestMapping m = SpringMvcUtil.readRequestMapping(classDoc, methodDoc);
        api.setRequestMapping(m);

        List<Param> params = new ArrayList<>(methodDoc.parameters().length);


        Map<String, String> paramCommentMap = new HashMap();
        for (ParamTag paramTag : methodDoc.paramTags()) {
            paramCommentMap.put(paramTag.parameterName(), paramTag.parameterComment());
        }

        for (Parameter parameter : methodDoc.parameters()) {
            params.add(new Param(parameter.type().qualifiedTypeName(), parameter.name(), paramCommentMap.get(parameter.name())));
        }

        api.setParams(params);

        api.setReturnType(methodDoc.returnType().toString());

        Tag[] returnTags = methodDoc.tags("return");
        if(returnTags.length > 0){
            api.setReturnComment(returnTags[0].text());
        }

        for (ThrowsTag throwsTag : methodDoc.throwsTags()) {
            api.putThrowExpections(throwsTag.exceptionName(), throwsTag.exceptionComment());
        }


        System.out.println(JSON.toJSONString(api));

    }
}
