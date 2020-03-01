package com.genx.javadoc.contants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/1 22:24
 */
public enum RoughlyType {

    Array,
    Object,
    String,
    Number,
    Boolean,
    Date;


    private static final Map<String, RoughlyType> BASE_TYPE_MAP = new HashMap(8);

    static {
        BASE_TYPE_MAP.put("byte", RoughlyType.Number);
        BASE_TYPE_MAP.put("java.lang.Byte", RoughlyType.Number);

        BASE_TYPE_MAP.put("short", RoughlyType.Number);
        BASE_TYPE_MAP.put("java.lang.Short", RoughlyType.Number);

        BASE_TYPE_MAP.put("int", RoughlyType.Number);
        BASE_TYPE_MAP.put("java.lang.Integer", RoughlyType.Number);

        BASE_TYPE_MAP.put("long", RoughlyType.Number);
        BASE_TYPE_MAP.put("java.lang.Long", RoughlyType.Number);

        BASE_TYPE_MAP.put("float", RoughlyType.Number);
        BASE_TYPE_MAP.put("java.lang.Float", RoughlyType.Number);

        BASE_TYPE_MAP.put("double", RoughlyType.Number);
        BASE_TYPE_MAP.put("java.lang.Double", RoughlyType.Number);

        BASE_TYPE_MAP.put("boolean", RoughlyType.Boolean);
        BASE_TYPE_MAP.put("java.lang.Boolean", RoughlyType.Number);

        BASE_TYPE_MAP.put("char", RoughlyType.String);
        BASE_TYPE_MAP.put("java.lang.Character", RoughlyType.Number);

        BASE_TYPE_MAP.put("java.lang.String", RoughlyType.String);

    }

    /**
     * 判断基础类型及其包装类
     * @param className
     * @return
     */
    public static RoughlyType assertBaseType(String className) {
        return BASE_TYPE_MAP.get(className);
    }
}
