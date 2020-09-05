package org.genx.javadoc.utils;

import org.apache.commons.lang3.StringUtils;
import org.genx.javadoc.bean.TypeParameterizedDoc;
import org.genx.javadoc.contants.RoughlyType;
import org.genx.javadoc.vo.*;
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

    private final JavaDocVO env;

    public DetailedTypeUtil(JavaDocVO env) {
        this.env = env;
    }

    public DetailedTypeDoc analysis(TypeDoc typeDoc) {

        //记录已解析的类名  避免死循环嵌套
        Map<String, Integer> resolvedMap = new HashMap(16);
        return analysis(typeDoc, resolvedMap);
    }

    private DetailedTypeDoc analysis(TypeDoc typeDoc, Map<String, Integer> resolvedMap) {

        DetailedTypeDoc detailedTypeDoc = DetailedTypeDoc.copyFromTypeDoc(typeDoc);

        ClassDocVO classDoc = env.getClassDoc(typeDoc.getClassName());
        if (classDoc != null) {

            detailedTypeDoc.setIterable(classDoc.isIterable());
            detailedTypeDoc.setType(classDoc.getType());

            //泛型要对上
            if (detailedTypeDoc.isIterable()) {
                //可迭代的
                analysisIterable(detailedTypeDoc, resolvedMap);
            } else if (RoughlyType.Object.name().equals(classDoc.getType())) {
                analysisObject(detailedTypeDoc, classDoc, resolvedMap);
            }

        } else {
            //如果是基础类型
            RoughlyType roughlyType = RoughlyType.assertBaseType(typeDoc.getClassName());
            if (roughlyType != null) {
                detailedTypeDoc.setType(roughlyType.name());
            }
        }
        return detailedTypeDoc;
    }

    private void analysisIterable(DetailedTypeDoc detailedTypeDoc, Map<String, Integer> resolvedMap) {
        if (detailedTypeDoc.getParameteres() != null && detailedTypeDoc.getParameteres().length > 0) {
            TypeParameterizedDoc itemParameterizedDoc = detailedTypeDoc.getParameteres()[0];

            DetailedTypeDoc item = new DetailedTypeDoc();

            item.setName("_item");
            item.setClassInfo(itemParameterizedDoc.getText());
            item.setClassName(itemParameterizedDoc.getClassName());
            item.setDimension(itemParameterizedDoc.getDimension());
            item.setParameteres(itemParameterizedDoc.getParameters());
            ClassDocVO itemClassDoc = env.getClassDoc(itemParameterizedDoc.getClassName());
            if (itemClassDoc != null && RoughlyType.Object.name().equals(itemClassDoc.getType())) {
                analysisObject(item, itemClassDoc, resolvedMap);
            }

            detailedTypeDoc.setData(Arrays.asList(item));
        }
    }

    //解析 Object 类型
    private void analysisObject(DetailedTypeDoc detailedTypeDoc, ClassDocVO classDoc, Map<String, Integer> resolvedMap) {
        detailedTypeDoc.setType(classDoc.getType());
        if (resolvedMap.containsKey(classDoc.getClassName()) || classDoc.getClassName().startsWith("java.")) {
            // java. 开头的包名 不解析
            return;
        }

        resolvedMap.put(classDoc.getClassName(), 1);

        Map<String, TypeParameterizedDoc> typeParameterizedDocMap = new HashMap(8);
        if (classDoc.getTypeParameters() != null && detailedTypeDoc.getParameteres() != null) {
            for (int i = 0; i < classDoc.getTypeParameters().size() && i < detailedTypeDoc.getParameteres().length; i++) {
                typeParameterizedDocMap.put(classDoc.getTypeParameters().get(i).getName(), detailedTypeDoc.getParameteres()[i]);
            }
        }

        Map<String, TypeDoc> fieldMap = new HashMap(16);
        for (TypeDoc field : classDoc.getFields()) {
            fieldMap.put(field.getName(), field);
        }


        List<DetailedTypeDoc> list = new ArrayList(classDoc.getMethods().size());

        TypeDoc field;
        for (MethodDocVO method : classDoc.getMethods()) {

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
            detailedTypeDoc.setData(list);
        }
    }


    private void mergeFieldInfoToMethod(TypeDoc field, TypeDoc methodReturnType) {
        if (StringUtils.isBlank(methodReturnType.getComment())) {
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
            typeDoc.setParameteres(typeParameterizedDoc.getParameters());
            typeDoc.setDimension(typeParameterizedDoc.getDimension());
        } else if (typeDoc.getParameteres() != null && typeDoc.getParameteres().length > 0) {
            matchTypeParameter(typeDoc.getParameteres(), typeParameterizedDocMap);
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
