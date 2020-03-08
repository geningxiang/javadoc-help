package org.genx.javadoc.utils;

import com.sun.javadoc.*;
import org.apache.commons.lang3.StringUtils;
import org.genx.javadoc.contants.RoughlyType;
import org.genx.javadoc.vo.TypeParameterizedDoc;

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
        if (classDoc != null && classDoc.interfaces() != null) {
            for (ClassDoc i : classDoc.interfaces()) {
                if (ITERABLE.equals(i.qualifiedName())) {
                    //如果实现了 java.lang.Iterable 接口 则认为是 可迭代的
                    return true;
                } else {
                    return isIterable(i);
                }
            }
        }
        return false;
    }

    public static boolean isMap(com.sun.javadoc.Type type) {
        if (type.asClassDoc() != null && type.asClassDoc() != null && type.asClassDoc().interfaces() != null) {
            for (ClassDoc i : type.asClassDoc().interfaces()) {
                if (MAP.equals(i.qualifiedName())) {
                    return true;
                } else {
                    return isIterable(i);
                }
            }
        }
        return false;
    }


    public static boolean isNumber(ClassDoc classDoc) {
        while (classDoc != null) {
            if (NUMBER.equals(classDoc.qualifiedTypeName())) {
                return true;
            }
            classDoc = classDoc.superclass();
        }
        return false;
    }

    public static boolean isString(ClassDoc classDoc) {
        if (classDoc != null && classDoc.interfaces() != null) {
            for (ClassDoc i : classDoc.interfaces()) {
                if (CHAR_SEQUENCE.equals(i.qualifiedName())) {
                    return true;
                } else {
                    return isString(i);
                }
            }
        }
        return false;
    }

    public static boolean isDate(ClassDoc classDoc) {
        while (classDoc != null) {
            if (DATE.equals(classDoc.qualifiedTypeName())) {
                return true;
            }
            classDoc = classDoc.superclass();
        }
        return false;
    }

    public static RoughlyType assertType(com.sun.javadoc.Type type) {
        //基础类型判断
        RoughlyType roughlyType = RoughlyType.assertBaseType(type.qualifiedTypeName());
        if (roughlyType != null) {
            return roughlyType;
        }
        if(type.asClassDoc() != null){
            return assertType(type.asClassDoc());
        }
        return RoughlyType.Unknow;
    }

    public static RoughlyType assertType(ClassDoc classDoc) {
        //基础类型判断
        RoughlyType roughlyType = RoughlyType.assertBaseType(classDoc.qualifiedTypeName());
        if (roughlyType != null) {
            return roughlyType;
        }
        if (isIterable(classDoc.asClassDoc())) {
            return RoughlyType.Array;
        }
        if (isNumber(classDoc.asClassDoc())) {
            return RoughlyType.Number;
        }
        if (isString(classDoc.asClassDoc())) {
            return RoughlyType.String;
        }
        if (isDate(classDoc.asClassDoc())) {
            return RoughlyType.Date;
        }
        return RoughlyType.Object;
    }

    public static Map<String, String> readTagMap(Doc type) {
        if (type.tags() != null && type.tags().length > 0) {
            Map<String, String> tagMap = new HashMap(16);
            String tmp;
            for (Tag tag : type.tags()) {
                tmp = tagMap.get(tag.name());
                if (StringUtils.isNotBlank(tmp)) {
                    tmp = tmp + System.lineSeparator() + tag.text();
                } else {
                    tmp = tag.text();
                }
                tagMap.put(tag.name(), tmp);
            }
            return tagMap;
        }
        return null;
    }

    /**
     * 读取指定的泛型
     * @param type
     * @return
     */
    public static TypeParameterizedDoc[] readParameteres(Type type) {
        if (type.asParameterizedType() != null) {
            TypeParameterizedDoc[] array = new TypeParameterizedDoc[type.asParameterizedType().typeArguments().length];
            Type item;
            for (int i = 0; i < type.asParameterizedType().typeArguments().length; i++) {
                item = type.asParameterizedType().typeArguments()[i];
                TypeParameterizedDoc typeParameterizedDoc = new TypeParameterizedDoc();
                typeParameterizedDoc.setText(item.toString());
                typeParameterizedDoc.setClassName(item.qualifiedTypeName());
                typeParameterizedDoc.setDimension(item.dimension().length() / 2 + 1);
                typeParameterizedDoc.setParameteres(readParameteres(item));
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
    public static Map<String, String> readThrowExpections(MethodDoc methodDoc) {
        Map<String, String> throwExpections = new HashMap(8);
        if (methodDoc.thrownExceptions() != null) {
            for (ClassDoc classDoc : methodDoc.thrownExceptions()) {
                throwExpections.put(classDoc.qualifiedTypeName(), "");
            }
        }

        for (ThrowsTag throwsTag : methodDoc.throwsTags()) {
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
