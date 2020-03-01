package com.genx.javadoc.utils;

import com.genx.javadoc.vo.TypeDoc;
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
public class TypeReader {

    private static final String GET = "get";


    public static TypeDoc read(Type type, String name, String comment) {
        //记录已解析的类型， 防止 StackOverflowError
        Map<Type, Integer> resolvedMap = new HashMap(16);
        TypeDoc typeDoc = read(type, name, comment, resolvedMap);
        return typeDoc;
    }


    private static TypeDoc read(Type type, String name, String comment, Map<Type, Integer> resolvedMap) {

        TypeDoc typeDoc = new TypeDoc();
        typeDoc.setClassInfo(type.toString());
        typeDoc.setClassName(type.qualifiedTypeName());
        typeDoc.setType(CoreUtil.assertType(type).name());

        typeDoc.setName(name);
        typeDoc.setComment(comment);

        typeDoc.setDimension(type.dimension().length() / 2);
        typeDoc.setIterable(CoreUtil.isIterable(type.asClassDoc()));

        if (resolvedMap.containsKey(type)) {
            return typeDoc;
        }
        resolvedMap.put(type, 1);
        if (typeDoc.isIterable()) {
            fillListItemType(type, typeDoc, resolvedMap);
        } else if (!CoreUtil.isBaseType(type)) {
            analysisObject(type, typeDoc, resolvedMap);
        }
        return typeDoc;
    }

    private static void fillListItemType(Type type, TypeDoc TypeDoc, Map<Type, Integer> resolvedMap) {
        List<TypeDoc> data = new ArrayList();
        //list 取泛型第一个
        if (type.asParameterizedType().typeArguments() != null && type.asParameterizedType().typeArguments().length > 0) {
            data.add(read(type.asParameterizedType().typeArguments()[0], "_item", "", resolvedMap));
        }
        TypeDoc.setData(data);
    }

    private static void analysisObject(Type type, TypeDoc TypeDoc, Map<Type, Integer> resolvedMap) {
        List<TypeDoc> data = new ArrayList();

        //读取一遍类的泛型和当前泛型匹配
        Map<String, Type> typeParameterMap = new HashMap(8);
        if (type.asClassDoc().typeParameters() != null && type.asClassDoc().typeParameters().length > 0) {
            for (int i = 0; i < type.asClassDoc().typeParameters().length && i < type.asParameterizedType().typeArguments().length; i++) {
                typeParameterMap.put(type.asClassDoc().typeParameters()[i].typeName(), type.asParameterizedType().typeArguments()[i]);
            }
        }

        //先读取一遍 字段上的注释
        Map<String, String> fieldCommentMap = new HashMap(32);
        //transient 关键字的 需要忽略
        Map<String, Integer> transientMap = new HashMap(32);
        for (FieldDoc field : type.asClassDoc().fields(false)) {
            fieldCommentMap.put(field.name(), field.getRawCommentText());

            if(Modifier.isTransient(field.modifierSpecifier())){
                transientMap.put(field.name(), 1);
            }
        }

        for (MethodDoc method : type.asClassDoc().methods()) {
            if (Modifier.isPublic(method.modifierSpecifier())
                    && !Modifier.isStatic(method.modifierSpecifier())
                    && method.parameters().length == 0
                    && method.name().startsWith(GET)
                    && method.name().length() > GET.length()
            ) {
                String methodName = method.name().substring(3, 4).toLowerCase() + method.name().substring(4);

                if(transientMap.containsKey(methodName)){
                    //忽略 transient 关键字的 字段
                    continue;
                }

                String comment = method.getRawCommentText();
                if (StringUtils.isBlank(comment)) {
                    //get方法上没有注释， 尝试读取字段上的注释
                    comment = fieldCommentMap.get(methodName);
                }

                Type match = typeParameterMap.get(method.returnType().qualifiedTypeName());
                if (match == null) {
                    match = method.returnType();
                }
                data.add(read(match, methodName, comment, resolvedMap));

            }
        }
        TypeDoc.setData(data);
    }


}
