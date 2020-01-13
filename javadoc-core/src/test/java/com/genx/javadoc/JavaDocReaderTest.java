package com.genx.javadoc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.genx.javadoc.utils.StringUtils;
import com.sun.javadoc.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 18:27
 */
public class JavaDocReaderTest {


    @Test
    public void test() throws IOException {


        List<String> classPathList = new ArrayList(512);
        classPathList.add("E:\\idea-workspace\\CaimaoProject\\FinanceSimulate\\target\\simulate-0.0.1-SNAPSHOT\\BOOT-INF\\classes");
        for (File file : new File("E:\\idea-workspace\\CaimaoProject\\FinanceSimulate\\target\\simulate-0.0.1-SNAPSHOT\\BOOT-INF\\lib").listFiles()) {
            classPathList.add(file.getAbsolutePath());
        }

        String path = "E:/idea-workspace/CaimaoProject/FinanceSimulate/src/main/java";
        ClassDoc[] classDocs = JavaDocReader.readWithClassDocs(new File(path), classPathList);

        for (ClassDoc classDoc : classDocs) {
            if ("com.caimao.finance.simulate.controller.DetailController".equals(classDoc.qualifiedTypeName())) {
                parseClassDoc(classDoc);
            }
        }

    }

    private void parseClassDoc(ClassDoc c) {

        System.out.println(c.toString());

        //泛型
        System.out.println("== c.typeParameters() ==");
        for (TypeVariable typeVariable : c.typeParameters()) {
            System.out.println(typeVariable);
        }


//        for (FieldDoc field : c.fields(false)) {
//            System.out.println("[field] "+field);
//            System.out.println("[field] "+field.type());
//        }


        for (MethodDoc methodDoc : c.methods()) {
            System.out.println("@@@@@@@@");
            System.out.println("[method] " + methodDoc);
            System.out.println("[method return] " + methodDoc.returnType());

            JSON json = parseReturnType(methodDoc.returnType(), null);

            System.err.println("【returnType】" + json);

//            for (Type type : methodDoc.returnType().asParameterizedType().typeArguments()) {
//                System.out.println(type);
//
//                //判断几维的  数组
//                System.err.println(type.dimension());
//
//
//
//                System.out.println(type.asClassDoc());
//
//                for (ClassDoc anInterface : type.asClassDoc().interfaces()) {
//                    System.out.println("【interface】"+anInterface);
//                }
//                if(type.asParameterizedType() != null)
//                for (Type type2 : type.asParameterizedType().typeArguments()) {
//                    System.out.println(type2);
//                }
//            }
//
//            System.out.println("== methodDoc.returnType() ==");
//            ClassDoc c1 = methodDoc.returnType().asClassDoc();
//            System.out.println(c1);
//
//            for (TypeVariable typeVariable : c1.typeParameters()) {
//                System.out.println(typeVariable);
//            }
//
//            for (MethodDoc method : c1.methods()) {
//                if (method.name().startsWith("get")) {
//                    System.out.println(method);
//                    System.out.println(method.returnType());
//                }
//            }
//
//        }
        }
    }


    private JSON parseReturnType(Type returnType, String comment) {
        //先判断几维的
        String dimension = returnType.dimension();
        if (dimension != null && dimension.length() > 0) {
            //数组
            JSONArray data = new JSONArray();
            JSONArray temp1 = data;
            for (int i = dimension.length() - 1; i >= 0; i--) {
                if (i > 0) {
                    JSONArray temp2 = new JSONArray();
                    temp1.add(temp2);
                    temp1 = temp2;
                } else {
                    temp1.add(parseReturnType(returnType.getElementType(), null));
                }
            }
            return data;
        } else if (isList(returnType)) {
            //List 只取 泛型第一个
            JSONArray data = new JSONArray();
            if (returnType.asParameterizedType() != null && returnType.asParameterizedType().typeArguments().length > 0) {
                data.add(parseReturnType(returnType.asParameterizedType().typeArguments()[0], null));
            }


            return data;
        } else if (isBaseType(returnType)) {
            JSONObject json = new JSONObject();
            if (returnType.asClassDoc() == null) {
                json.put("__javaType", returnType.simpleTypeName());
            } else {
                json.put("__javaType", returnType.asClassDoc().name());
            }
            json.put("__comment", comment);
            return json;
        } else {
            JSONObject json = new JSONObject();

            Map<String, Type> typeVariablMap = new HashMap(8);
            for (int i = 0; i < returnType.asClassDoc().typeParameters().length; i++) {
                if (returnType.asParameterizedType() != null && returnType.asParameterizedType().typeArguments().length > i) {
                    typeVariablMap.put(returnType.asClassDoc().typeParameters()[i].typeName(), returnType.asParameterizedType().typeArguments()[i]);
                }
            }

            Map<String, String> commentMap = new HashMap(32);
            for (FieldDoc field : returnType.asClassDoc().fields(false)) {
                commentMap.put(field.name(), field.commentText());
            }

            for (MethodDoc method : returnType.asClassDoc().methods()) {
                if (method.name().startsWith("get")) {
                    //get 开头的方法

                    Type methodReturnType = method.returnType();
                    if (methodReturnType.asTypeVariable() != null && typeVariablMap.containsKey(methodReturnType.asTypeVariable().typeName())) {
                        methodReturnType = typeVariablMap.get(methodReturnType.asTypeVariable().typeName());
                    }
                    String name =method.name().substring(3, 4).toLowerCase() + method.name().substring(4);
                    json.put(name, parseReturnType(methodReturnType, method.commentText() + commentMap.get(name)));
                }
            }
            return json;
        }
    }

    private boolean isList(Type type) {
        if (type.asClassDoc() != null) {
            for (ClassDoc anInterface : type.asClassDoc().interfaces()) {
//            System.out.println("【interface】"+anInterface);
                if ("Collection".equals(anInterface.asClassDoc().simpleTypeName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isBaseType(Type type) {
        if (type.asClassDoc() == null) {
            //基础类型 为空  例如 int double long
            return true;
        }
        if (type.asClassDoc().qualifiedTypeName().startsWith("java.")) {
            return true;
        }
        return false;
    }


}