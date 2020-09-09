package org.genx.javadoc.utils;

import org.genx.javadoc.bean.*;
import org.genx.javadoc.bean.rest.RestNestTypeDoc;
import org.genx.javadoc.bean.rest.RestTypeDoc;
import org.genx.javadoc.contants.RoughlyType;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2020/3/8 14:47
 */
public class DetailedTypeUtil {
    private static final String ITERABLE = "java.lang.Iterable";


    private final JavaDoc env;

    public DetailedTypeUtil(JavaDoc env) {
        this.env = env;
    }

    public RestNestTypeDoc analysis(TypeDoc typeDoc) {

        //记录已解析的类名  避免死循环嵌套
        Map<String, Integer> resolvedMap = new HashMap(16);
        return analysis(typeDoc, resolvedMap);
    }

    private RestNestTypeDoc analysis(TypeDoc typeDoc, Map<String, Integer> resolvedMap) {

        RestNestTypeDoc restTypeDoc = RestNestTypeDoc.copyFromTypeDoc(typeDoc);

        ClassDoc classDoc = env.getClassDoc(typeDoc.getClassName());
        if (classDoc != null) {

            restTypeDoc.setIterable(isIterable(classDoc));
            RoughlyType roughlyType = CoreUtil.assertType(classDoc);

            restTypeDoc.setType(roughlyType.name());

            //泛型要对上
            if (restTypeDoc.isIterable()) {
                //可迭代的
                analysisIterable(restTypeDoc, resolvedMap);
            } else if (RoughlyType.Object.name().equals(restTypeDoc.getType())) {
                analysisObject(restTypeDoc, classDoc, resolvedMap);
            }

        } else {
            //如果是基础类型
            RoughlyType roughlyType = RoughlyType.assertBaseType(typeDoc.getClassName());
            if (roughlyType != null) {
                restTypeDoc.setType(roughlyType.name());
            }
        }
        return restTypeDoc;
    }

    private boolean isIterable(ClassDoc classDoc){
        return classDoc.getInterfaceTypes().contains(ITERABLE);
    }

    private void analysisIterable(RestNestTypeDoc restTypeDoc, Map<String, Integer> resolvedMap) {
        if (restTypeDoc.getParameters() != null && restTypeDoc.getParameters().length > 0) {
            TypeParameterizedDoc itemParameterizedDoc = restTypeDoc.getParameters()[0];

            RestNestTypeDoc item = new RestNestTypeDoc();

            item.setName("_item");
            item.setClassInfo(itemParameterizedDoc.getText());
            item.setClassName(itemParameterizedDoc.getClassName());
            item.setDimension(itemParameterizedDoc.getDimension());
            item.setParameters(itemParameterizedDoc.getParameters());
            ClassDoc itemClassDoc = env.getClassDoc(itemParameterizedDoc.getClassName());
            RoughlyType roughlyType =  CoreUtil.assertType(itemClassDoc);
            if (itemClassDoc != null && RoughlyType.Object == roughlyType) {
                analysisObject(item, itemClassDoc, resolvedMap);
            }

            restTypeDoc.setData(Arrays.asList(item));
        }
    }

    //解析 Object 类型
    private void analysisObject(RestNestTypeDoc restTypeDoc, ClassDoc classDoc, Map<String, Integer> resolvedMap) {
        RoughlyType roughlyType =  CoreUtil.assertType(classDoc);
        restTypeDoc.setType(roughlyType.name());
        if (resolvedMap.containsKey(classDoc.getClassName()) || classDoc.getClassName().startsWith("java.")) {
            // java. 开头的包名 不解析
            return;
        }

        resolvedMap.put(classDoc.getClassName(), 1);

        Map<String, TypeParameterizedDoc> typeParameterizedDocMap = new HashMap(8);
        if (classDoc.getTypeParameters() != null && restTypeDoc.getParameters() != null) {
            for (int i = 0; i < classDoc.getTypeParameters().size() && i < restTypeDoc.getParameters().length; i++) {
                typeParameterizedDocMap.put(classDoc.getTypeParameters().get(i).getName(), restTypeDoc.getParameters()[i]);
            }
        }

        Map<String, TypeDoc> fieldMap = new HashMap(16);
        for (TypeDoc field : classDoc.getFields().values()) {
            fieldMap.put(field.getName(), field);
        }
        if(classDoc.getMethods() == null){
            return;
        }

        List<RestTypeDoc> list = new ArrayList(classDoc.getMethods().size());

        TypeDoc field;
        for (MethodDoc method : classDoc.getMethods().values()) {

            if (Modifier.isPublic(method.getModifierSpecifier())
                    && !Modifier.isStatic(method.getModifierSpecifier())
                    && method.hasNoParam()
                    && method.getMethodName().startsWith("get")) {

                String name = CoreUtil.lowCase(method.getMethodName().substring(3));

                //这里必须 深度复制
                TypeDoc typeDoc = method.getReturnType().copy();
                typeDoc.setName(name);

                field = fieldMap.get(name);
                if (field != null) {
                    if (Modifier.isTransient(field.getModifierSpecifier())) {
                        //transient 标识
                        continue;
                    }
                    mergeFieldInfoToMethod(field, typeDoc);
                }

                matchTypeParameter(typeDoc, typeParameterizedDocMap);

                list.add(analysis(typeDoc, resolvedMap));
            }
        }
        if (list.size() > 0) {
            restTypeDoc.setData(list);
        }
    }


    private void mergeFieldInfoToMethod(TypeDoc field, TypeDoc methodReturnType) {
        if (methodReturnType.getComment() == null) {
            methodReturnType.setComment(field.getComment());
        }
        if (field.getLimits() != null && field.getLimits().size() > 0) {
            methodReturnType.addLimits(field.getLimits());
        }

    }

    /**
     * 匹配泛型
     * @param typeDoc
     * @param typeParameterizedDocMap
     */
    private void matchTypeParameter(TypeDoc typeDoc, Map<String, TypeParameterizedDoc> typeParameterizedDocMap) {
        TypeParameterizedDoc typeParameterizedDoc = typeParameterizedDocMap.get(typeDoc.getClassName());
        if (typeParameterizedDoc != null) {
            typeDoc.setClassName(typeParameterizedDoc.getClassName());
            typeDoc.setClassInfo(typeParameterizedDoc.getText());
            typeDoc.setParameters(typeParameterizedDoc.getParameters());
            typeDoc.setDimension(typeParameterizedDoc.getDimension());
        } else if (typeDoc.getParameters() != null && typeDoc.getParameters().length > 0) {
            matchTypeParameter(typeDoc.getParameters(), typeParameterizedDocMap);
        }
    }

    /**
     * 深度匹配泛型
     * 比如类的定义 <T> 但是方法返回类型是 List<T>
     * @param tt
     * @param typeParameterizedDocMap
     */
    private void matchTypeParameter(TypeParameterizedDoc[] tt, Map<String, TypeParameterizedDoc> typeParameterizedDocMap) {
        TypeParameterizedDoc temp;
        for (TypeParameterizedDoc typeParameterizedDoc : tt) {
            temp = typeParameterizedDocMap.get(typeParameterizedDoc.getClassName());
            if (temp != null) {
                BeanUtils.copyProperties(temp, typeParameterizedDoc);
            } else if (typeParameterizedDoc.getParameters() != null && typeParameterizedDoc.getParameters().length > 0) {
                matchTypeParameter(typeParameterizedDoc.getParameters(), typeParameterizedDocMap);
            }
        }
    }

}
