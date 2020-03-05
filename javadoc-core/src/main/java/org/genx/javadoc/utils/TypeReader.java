package org.genx.javadoc.utils;

import com.sun.javadoc.*;
import org.apache.commons.lang3.StringUtils;
import org.genx.javadoc.vo.TypeDoc;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/29 23:09
 */
public class TypeReader {

    private static final String GET = "get";


    public static TypeDoc read(Parameter parameter, String comment) {
        return read(parameter.type(), parameter.name(), comment, parameter.annotations());
    }

    public static TypeDoc read(Type type, String name, String comment, AnnotationDesc[] annotations) {
        //记录已解析的类型， 防止 StackOverflowError
        Map<Type, Integer> resolvedMap = new HashMap(16);
        TypeDoc typeDoc = read(type, name, comment, annotations, resolvedMap, null);
        return typeDoc;
    }


    /**
     *
     * @param type
     * @param name
     * @param comment
     * @param resolvedMap
     * @param typeParameterMap
     * @return
     */
    private static TypeDoc read(Type type, String name, String comment, AnnotationDesc[] annotations, Map<Type, Integer> resolvedMap, Map<String, Type> typeParameterMap) {
        if (type.asClassDoc() != null && Object.class.getName().equals(type.asClassDoc().qualifiedTypeName())) {
            return TypeDoc.ofObject(type.toString(), name, comment);
        }

        TypeDoc typeDoc = new TypeDoc();
        typeDoc.setClassInfo(type.toString());
        typeDoc.setClassName(type.qualifiedTypeName());
        typeDoc.setName(name);
        typeDoc.setComment(comment);

        typeDoc.setDimension(type.dimension().length() / 2);
        typeDoc.setIterable(CoreUtil.isIterable(type.asClassDoc()));

        typeDoc.setAnnotations(AnnotationUtil.readAnnotationMap(annotations));

        if (resolvedMap.containsKey(type)) {
            return typeDoc;
        }
        resolvedMap.put(type, 1);
        if (typeDoc.isIterable()) {
            fillListItemType(type, typeDoc, resolvedMap, typeParameterMap);
        } else if (CoreUtil.isBaseType(type)) {

        } else if (CoreUtil.isMap(type)) {

        } else {
            analysisObject(type, typeDoc, resolvedMap);
        }
        return typeDoc;
    }

    private static void fillListItemType(Type type, TypeDoc typeDoc, Map<Type, Integer> resolvedMap, Map<String, Type> typeParameterMap) {
        List<TypeDoc> data = new ArrayList();
        //list 取泛型第一个
        if (type != null && type.asParameterizedType() != null && type.asParameterizedType().typeArguments() != null && type.asParameterizedType().typeArguments().length > 0) {
            Type parameterType = type.asParameterizedType().typeArguments()[0];

            if (typeParameterMap != null && typeParameterMap.containsKey(parameterType.qualifiedTypeName())) {
                parameterType = typeParameterMap.get(parameterType.qualifiedTypeName());
            }
            if (parameterType != null) {
                data.add(read(parameterType, "_item", "", null, resolvedMap, null));
            }
        }
        typeDoc.setData(data);
    }

    private static void analysisObject(Type type, TypeDoc typeDoc, Map<Type, Integer> resolvedMap) {

        //读取一遍类的泛型和当前泛型匹配
        Map<String, Type> typeParameterMap = new HashMap(8);
        if (type.asClassDoc().typeParameters() != null && type.asClassDoc().typeParameters().length > 0 && type.asParameterizedType() != null) {
            for (int i = 0; i < type.asClassDoc().typeParameters().length && i < type.asParameterizedType().typeArguments().length; i++) {
                typeParameterMap.put(type.asClassDoc().typeParameters()[i].typeName(), type.asParameterizedType().typeArguments()[i]);
            }
        }

        LinkedHashMap<String, TypeDoc> methodMap = new LinkedHashMap();

        boolean lombokData = AnnotationUtil.hasAnnotation(type.asClassDoc(), "lombok.Data");

        //先读取一遍 字段上的注释
        Map<String, String> fieldCommentMap = new HashMap(32);
        //transient 关键字的 需要忽略
        Map<String, Integer> transientMap = new HashMap(32);
        for (FieldDoc field : type.asClassDoc().fields(false)) {

            String fieldComment = field.getRawCommentText();

            String[] apiModelValue = AnnotationUtil.readAnnotationValue(field, "io.swagger.annotations.ApiModelProperty", "value");
            if (apiModelValue != null && apiModelValue.length > 0 && StringUtils.isNotBlank(apiModelValue[0])) {
                fieldComment = apiModelValue[0];
            }

            fieldCommentMap.put(field.name(), fieldComment);

            if (Modifier.isTransient(field.modifierSpecifier())) {
                transientMap.put(field.name(), 1);
            } else if (lombokData) {
                methodMap.put(field.name(), read(field.type(), field.name(), field.getRawCommentText(), field.annotations(), resolvedMap, typeParameterMap));
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

                if (transientMap.containsKey(methodName)) {
                    //忽略 transient 关键字的 字段
                    continue;
                }

                String comment = method.getRawCommentText();
                if (StringUtils.isBlank(comment)) {
                    //get方法上没有注释， 尝试读取字段上的注释
                    comment = fieldCommentMap.get(methodName);
                }
                Type returnType = typeParameterMap.get(method.returnType().qualifiedTypeName());
                if (returnType == null) {
                    returnType = method.returnType();
                }

                methodMap.put(methodName, read(returnType, methodName, comment, null, resolvedMap, typeParameterMap));

            }
        }
        typeDoc.setData(methodMap.values());
    }


}
