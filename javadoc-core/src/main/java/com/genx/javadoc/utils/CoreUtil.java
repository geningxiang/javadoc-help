package com.genx.javadoc.utils;

import com.genx.javadoc.contants.RoughlyType;
import com.genx.javadoc.vo.AnnotationVO;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ProgramElementDoc;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
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

    public static boolean isMap(com.sun.javadoc.Type type){
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
        while(classDoc != null){
            if(NUMBER.equals(classDoc.qualifiedTypeName())){
                return true;
            }
            classDoc = classDoc.superclass();
        }
        return false;
    }

    public static boolean isString(ClassDoc classDoc){
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

    public static boolean isDate(ClassDoc classDoc){
        while(classDoc != null){
            if(DATE.equals(classDoc.qualifiedTypeName())){
                return true;
            }
            classDoc = classDoc.superclass();
        }
        return false;
    }

    public static RoughlyType assertType(com.sun.javadoc.Type type) {
        //基础类型判断
        RoughlyType roughlyType = RoughlyType.assertBaseType(type.qualifiedTypeName());
        if(roughlyType != null){
            return roughlyType;
        }
        if(isIterable(type.asClassDoc())){
            return RoughlyType.Array;
        }
        if(isNumber(type.asClassDoc())){
            return RoughlyType.Number;
        }
        if(isString(type.asClassDoc())){
            return RoughlyType.String;
        }
        if(isDate(type.asClassDoc())){
            return RoughlyType.Date;
        }

        return RoughlyType.Object;
    }

}
