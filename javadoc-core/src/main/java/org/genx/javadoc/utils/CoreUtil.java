package org.genx.javadoc.utils;

import org.apache.commons.lang3.StringUtils;
import org.genx.javadoc.bean.ClassDoc;
import org.genx.javadoc.bean.TypeDoc;
import org.genx.javadoc.bean.TypeParameterizedDoc;
import org.genx.javadoc.contants.RoughlyType;

import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/1 22:25
 */
public class CoreUtil {
    private static final String JAVA_DOT = "java.";

    private static final String ITERABLE = "java.lang.Iterable";

    private static final String NUMBER = "java.lang.Number";

    private static final String CHAR_SEQUENCE = "java.lang.CharSequence";

    private static final String DATE = "java.util.Date";

    private static final String MAP = "java.util.Map";

    /**
     * 是否基础类型
     * @param type
     * @return
     */
    public static boolean isBaseType(com.sun.javadoc.Type type) {
        //这里包含了 java.util包 所以必须先判断是否 可迭代
        return type.asClassDoc() == null || type.qualifiedTypeName().startsWith(JAVA_DOT);
    }

    /**
     * 是否可迭代的
     * @param classDoc
     * @return
     */
    public static boolean isIterable(ClassDoc classDoc) {
        if (classDoc != null) {
            return classDoc.getInterfaceTypes().contains(ITERABLE);
        }
        return false;
    }

    public static boolean isMap(ClassDoc classDoc) {
        if (classDoc != null) {
            return classDoc.getInterfaceTypes().contains(MAP);
        }
        return false;
    }


    public static boolean isNumber(ClassDoc classDoc) {
        if (classDoc != null) {
            return classDoc.getInterfaceTypes().contains(NUMBER);
        }
        return false;
    }

    public static boolean isString(ClassDoc classDoc) {
        if (classDoc != null) {
            return classDoc.getInterfaceTypes().contains(CHAR_SEQUENCE);
        }
        return false;
    }

    public static boolean isDate(ClassDoc classDoc) {
        if (classDoc != null) {
            //TODO 要不要在 classDoc上加上父类 信息
            //Date不是接口  也没有标记父类， 只好先判断一下方法
            return classDoc.getMethods().containsKey("before(java.util.Date)") && classDoc.getMethods().containsKey("after(java.util.Date)");
        }
        return false;
    }

    public static RoughlyType assertType(ClassDoc classDoc) {
        //基础类型判断
        RoughlyType roughlyType = RoughlyType.assertBaseType(classDoc.getClassName());
        if (roughlyType != null) {
            return roughlyType;
        }
        if (isIterable(classDoc)) {
            return RoughlyType.Array;
        }
        if (isMap(classDoc)) {
            return RoughlyType.Map;
        }
        if (isNumber(classDoc)) {
            return RoughlyType.Number;
        }
        if (isString(classDoc)) {
            return RoughlyType.String;
        }
        if (isDate(classDoc)) {
            return RoughlyType.Date;
        }
        return RoughlyType.Object;
    }


    /**
     * 读取指定的泛型
     * @param type
     * @return
     */
    public static TypeParameterizedDoc[] readParameteres(com.sun.javadoc.Type type) {
        if (type.asParameterizedType() != null) {
            TypeParameterizedDoc[] array = new TypeParameterizedDoc[type.asParameterizedType().typeArguments().length];
            com.sun.javadoc.Type item;
            for (int i = 0; i < type.asParameterizedType().typeArguments().length; i++) {
                item = type.asParameterizedType().typeArguments()[i];
                TypeParameterizedDoc typeParameterizedDoc = new TypeParameterizedDoc();
                typeParameterizedDoc.setText(item.toString());
                typeParameterizedDoc.setClassName(item.qualifiedTypeName());
                // item.dimension() 返回 "" "[]" "[][]"
                typeParameterizedDoc.setDimension(item.dimension().length() / 2 + 1);
                typeParameterizedDoc.setParameters(readParameteres(item));
                array[i] = typeParameterizedDoc;
            }
            return array;
        }
        return null;
    }


    /**
     * 读取方法的抛出异常
     * @param methodDoc
     * @return
     */
    public static Map<String, String> readThrowExpections(com.sun.javadoc.MethodDoc methodDoc) {
        Map<String, String> throwExpections = new HashMap(8);
        if (methodDoc.thrownExceptions() != null) {
            for (com.sun.javadoc.ClassDoc classDoc : methodDoc.thrownExceptions()) {
                throwExpections.put(classDoc.qualifiedTypeName(), "");
            }
        }

        for (com.sun.javadoc.ThrowsTag throwsTag : methodDoc.throwsTags()) {
            if (throwsTag.exceptionType() != null) {
                throwExpections.put(throwsTag.exceptionType().qualifiedTypeName(), throwsTag.exceptionComment());
            }
        }
        return throwExpections;
    }


    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        if (StringUtils.isNotBlank(str)) {
            char[] ch = str.toCharArray();
            if (ch[0] >= 'a' && ch[0] <= 'z') {
                ch[0] = (char) (ch[0] - 32);
            }
            return new String(ch);
        }
        return "";
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String lowCase(String str) {
        if (StringUtils.isNotBlank(str)) {
            char[] ch = str.toCharArray();
            if (ch[0] >= 'A' && ch[0] <= 'Z') {
                ch[0] = (char) (ch[0] + 32);
            }
            return new String(ch);
        }
        return "";
    }

}
