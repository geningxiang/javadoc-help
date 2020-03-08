package org.genx.javadoc.utils;

import org.genx.javadoc.contants.RoughlyType;
import org.genx.javadoc.vo.ClassDocVO;
import org.genx.javadoc.vo.DetailedTypeDoc;
import org.genx.javadoc.vo.JavaDocVO;
import org.genx.javadoc.vo.TypeDoc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/8 14:47
 */
public class DetailedTypeUtil {

    public static DetailedTypeDoc analysis(TypeDoc typeDoc, JavaDocVO env) {
        //记录已解析的类名  避免死循环嵌套
        Map<String, Integer> resolvedMap = new HashMap(16);
        return analysis(typeDoc, env, resolvedMap);
    }

    private static DetailedTypeDoc analysis(TypeDoc typeDoc, JavaDocVO env, Map<String, Integer> resolvedMap) {

        DetailedTypeDoc detailedTypeDoc = DetailedTypeDoc.copyFromTypeDoc(typeDoc);

        ClassDocVO classDoc = env.getClassDoc(typeDoc.getClassName());
        if (classDoc != null) {

            detailedTypeDoc.setIterable(classDoc.isIterable());
            detailedTypeDoc.setType(classDoc.getType());

            //泛型要对上
        } else {
            //如果是基础类型
            RoughlyType roughlyType = RoughlyType.assertBaseType(typeDoc.getClassName());
            if(roughlyType != null) {
                detailedTypeDoc.setType(roughlyType.name());
            }
        }
        return detailedTypeDoc;

    }

//    private static void fillListItemType(Type type, TypeDoc typeDoc, Map<Type, Integer> resolvedMap, Map<String, Type> typeParameterMap) {
//        List<TypeDoc> data = new ArrayList();
//        //list 取泛型第一个
//        if (type != null && type.asParameterizedType() != null && type.asParameterizedType().typeArguments() != null && type.asParameterizedType().typeArguments().length > 0) {
//            Type parameterType = type.asParameterizedType().typeArguments()[0];
//
//            if (typeParameterMap != null && typeParameterMap.containsKey(parameterType.qualifiedTypeName())) {
//                parameterType = typeParameterMap.get(parameterType.qualifiedTypeName());
//            }
//            if (parameterType != null) {
//                data.add(read(parameterType, "_item", "", resolvedMap, null));
//            }
//        }
//        typeDoc.setData(data);
//    }
}
