package com.genx.javadoc.parse;

import com.alibaba.fastjson.JSONObject;
import com.genx.javadoc.filter.IJavaDocFilter;
import com.genx.javadoc.reader.AnnotationDescReader;
import com.genx.javadoc.utils.SpringMvcUtil;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

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

        System.out.println(classDoc.qualifiedTypeName() + "#" + methodDoc.name());

        String url = SpringMvcUtil.getApiUrl(classDoc, methodDoc);
        System.out.println("接口地址:" + url);

    }
}
