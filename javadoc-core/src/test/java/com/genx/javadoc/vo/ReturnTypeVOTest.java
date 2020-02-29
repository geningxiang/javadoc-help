package com.genx.javadoc.vo;


import com.alibaba.fastjson.JSON;
import com.genx.javadoc.JavaDocReader;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/29 20:03
 */

public class ReturnTypeVOTest {

    @Test
    public void test2() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/");

        String path = resource.getFile().getAbsolutePath();

        List compilePathList = new ArrayList();
        compilePathList.add(path + "/../../../javadoc-demo/target/javadoc-demo/WEB-INF/classes");

        File libDir = new File(path + "/../../../javadoc-demo/target/javadoc-demo/WEB-INF/lib/");
        for (File file : libDir.listFiles()) {
            compilePathList.add(file.getAbsolutePath());
        }

        File sourceDirectory = new File(path + "/../../../javadoc-demo/src/main/java/");
        ClassDoc[] classDocs = JavaDocReader.readWithClassDocs(sourceDirectory, compilePathList);


        for (ClassDoc classDoc : classDocs) {
            if (classDoc.qualifiedTypeName().startsWith("com.genx.javadoc.controller")) {
                System.out.println("==========================");
                System.out.println(classDoc.qualifiedName());

                for (MethodDoc method : classDoc.methods()) {
                    System.out.println("#########");
                    System.out.println(method);

                    System.out.println(method.returnType().qualifiedTypeName());

                    System.out.println(method.returnType().asParameterizedType());

                    String returnComment = "";
                    //读取 return 注释
                    Tag[] returnTags = method.tags("return");
                    if (returnTags.length > 0) {
                        returnComment = returnTags[0].text();
                    }

                    ReturnTypeVO returnTypeVO = readReturnType(method.returnType(), "", returnComment);

                    System.out.println(JSON.toJSONString(returnTypeVO));

//                    for (Type type : method.returnType().asParameterizedType().typeArguments()) {
//                        System.out.println(type);
//                        System.out.println("是否list:" + isIterable(type.asClassDoc()));
//                        if (type.asParameterizedType() != null)
//                            for (Type type2 : type.asParameterizedType().typeArguments()) {
//                                System.out.println("    " + type2);
//                            }
//                    }
                }
            }
        }

    }

    private ReturnTypeVO readReturnType(Type type, String name, String comment) {
        System.out.println("Type=" + type);
        ReturnTypeVO returnTypeVO = new ReturnTypeVO();
        returnTypeVO.setClassInfo(type.toString());
        returnTypeVO.setName(name);
        returnTypeVO.setComment(comment);
        returnTypeVO.setClassName(type.qualifiedTypeName());
        returnTypeVO.setDimension(type.dimension().length() / 2);

        returnTypeVO.setIterable(isIterable(type.asClassDoc()));


        if (returnTypeVO.isIterable()) {
            List<ReturnTypeVO> data = new ArrayList();
            //list 取泛型第一个
            if (type.asParameterizedType().typeArguments() != null) {

                data.add(readReturnType(type.asParameterizedType().typeArguments()[0], "", ""));
            }
            returnTypeVO.setData(data);
        } else if (type.asClassDoc() != null
                && !type.qualifiedTypeName().startsWith("java.lang.")
                    && !type.qualifiedTypeName().startsWith("java.util.")
                && !type.qualifiedTypeName().startsWith("java.math.")
        ) {

            List<ReturnTypeVO> data = new ArrayList();
            Map<String, Type> typeParameterMap = new HashMap();

            //类的泛型
            if (type.asClassDoc().typeParameters() != null && type.asClassDoc().typeParameters().length > 0) {
                for (int i = 0; i < type.asClassDoc().typeParameters().length && i < type.asParameterizedType().typeArguments().length; i++) {
                    typeParameterMap.put(type.asClassDoc().typeParameters()[i].typeName(), type.asParameterizedType().typeArguments()[i]);
                }
            }

            JSON.toJSONString(null);


            //对象  取get方法
            for (MethodDoc method : type.asClassDoc().methods()) {
                if (Modifier.isPublic(method.modifierSpecifier())
                        && !Modifier.isStatic(method.modifierSpecifier())
                        && method.parameters().length == 0
                        && method.name().startsWith("get")
                ) {
                    String methodName = method.name().substring(3,4).toLowerCase() + method.name().substring(4);
                    Type match = typeParameterMap.get(method.returnType().qualifiedTypeName());
                    if (match != null) {
                        data.add(readReturnType(match, methodName, method.commentText()));
                    } else {
                        data.add(readReturnType(method.returnType(), methodName, method.commentText()));
                    }

                }
            }
            returnTypeVO.setData(data);
        }


        return returnTypeVO;
    }

    private boolean isIterable(ClassDoc classDoc) {
        if (classDoc != null && classDoc.interfaces() != null) {
            for (ClassDoc anInterface : classDoc.interfaces()) {
                if ("java.lang.Iterable".equals(anInterface.qualifiedName())) {
                    return true;
                } else {
                    return isIterable(anInterface);
                }
            }
        }
        return false;
    }

}
