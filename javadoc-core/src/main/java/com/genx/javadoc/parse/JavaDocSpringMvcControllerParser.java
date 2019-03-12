package com.genx.javadoc.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.genx.javadoc.filter.IJavaDocFilter;
import com.genx.javadoc.utils.SpringMvcUtil;
import com.genx.javadoc.vo.ClassDocVO;
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

        ClassDocVO classDocVO = new ClassDocVO(classDoc);
    }
}
