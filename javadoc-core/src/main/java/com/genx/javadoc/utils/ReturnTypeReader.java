package com.genx.javadoc.utils;

import com.genx.javadoc.vo.ReturnTypeVO;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Type;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/29 23:09
 */
public class ReturnTypeReader {

    private static final String GET = "get";

    public static ReturnTypeVO read(Type type) {
        return read(type, "", "");
    }

    private static ReturnTypeVO read(Type type, String name, String comment) {
        ReturnTypeVO returnTypeVO = new ReturnTypeVO();
        returnTypeVO.setClassInfo(type.toString());
        returnTypeVO.setName(name);
        returnTypeVO.setComment(comment);
        returnTypeVO.setClassName(type.qualifiedTypeName());
        returnTypeVO.setDimension(type.dimension().length() / 2);

        returnTypeVO.setIterable(isIterable(type.asClassDoc()));

        if (returnTypeVO.isIterable()) {
            fillListItemType(type, returnTypeVO);
        } else if (!isBaseType(type)) {
            analysisObject(type, returnTypeVO);
        }
        return returnTypeVO;
    }

    private static void fillListItemType(Type type, ReturnTypeVO returnTypeVO) {
        List<ReturnTypeVO> data = new ArrayList();
        //list 取泛型第一个
        if (type.asParameterizedType().typeArguments() != null && type.asParameterizedType().typeArguments().length > 0) {
            data.add(read(type.asParameterizedType().typeArguments()[0], "_item", ""));
        }
        returnTypeVO.setData(data);
    }

    private static void analysisObject(Type type, ReturnTypeVO returnTypeVO) {
        List<ReturnTypeVO> data = new ArrayList();

        //读取一遍类的泛型和当前泛型匹配
        Map<String, Type> typeParameterMap = new HashMap(8);
        if (type.asClassDoc().typeParameters() != null && type.asClassDoc().typeParameters().length > 0) {
            for (int i = 0; i < type.asClassDoc().typeParameters().length && i < type.asParameterizedType().typeArguments().length; i++) {
                typeParameterMap.put(type.asClassDoc().typeParameters()[i].typeName(), type.asParameterizedType().typeArguments()[i]);
            }
        }

        //先读取一遍 字段上的注释
        Map<String, String> fieldCommentMap = new HashMap(32);
        for (FieldDoc field : type.asClassDoc().fields(false)) {
            fieldCommentMap.put(field.name(), field.getRawCommentText());
        }

        for (MethodDoc method : type.asClassDoc().methods()) {

            if (Modifier.isPublic(method.modifierSpecifier())
                    && !Modifier.isStatic(method.modifierSpecifier())
                    && method.parameters().length == 0
                    && method.name().startsWith(GET)
                    && method.name().length() > GET.length()
            ) {
                String methodName = method.name().substring(3, 4).toLowerCase() + method.name().substring(4);

                String comment =  method.getRawCommentText();
                if (StringUtils.isBlank(comment)) {
                    //get方法上没有注释， 尝试读取字段上的注释
                    comment = fieldCommentMap.get(methodName);
                }

                Type match = typeParameterMap.get(method.returnType().qualifiedTypeName());
                if (match != null) {
                    data.add(read(match, methodName, comment));
                } else {
                    data.add(read(method.returnType(), methodName, comment));
                }
            }
        }
        returnTypeVO.setData(data);
    }


    /**
     * 是否基础类型
     * @param type
     * @return
     */
    public static boolean isBaseType(Type type) {
        //这里包含了 java.util包 所以必须先判断是否 可迭代
        return type.asClassDoc() == null || type.qualifiedTypeName().startsWith("java.");
    }

    /**
     * 是否可迭代的
     * @param classDoc
     * @return
     */
    public static boolean isIterable(ClassDoc classDoc) {
        if (classDoc != null && classDoc.interfaces() != null) {
            for (ClassDoc i : classDoc.interfaces()) {
                if ("java.lang.Iterable".equals(i.qualifiedName())) {
                    //如果实现了 java.lang.Iterable 接口 则认为是 可迭代的
                    return true;
                } else {
                    return isIterable(i);
                }
            }
        }
        return false;
    }
}
