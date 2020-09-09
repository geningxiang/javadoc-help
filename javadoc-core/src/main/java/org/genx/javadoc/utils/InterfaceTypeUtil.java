package org.genx.javadoc.utils;

import com.sun.javadoc.ClassDoc;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/5 22:43
 */
public class InterfaceTypeUtil {

    /**
     * 读取当前类实现的接口 会一层一层网上找
     * @param classDoc
     * @return
     */
    public static Set<String> readInterfaceTypes(ClassDoc classDoc) {
        Set<String> interfaceTypes = new HashSet(16);
        Set<String> processed = new HashSet(16);
        processed.add(Object.class.getName());
        readInterfaceTypes(interfaceTypes, classDoc, processed);
        return interfaceTypes;
    }

    private static void readInterfaceTypes(Set<String> interfaceTypes, ClassDoc classDoc, Set<String> processed) {
        if (classDoc == null || processed.contains(classDoc.qualifiedTypeName())) {
            return;
        }
        processed.add(classDoc.qualifiedTypeName());

        for (ClassDoc type : classDoc.interfaces()) {
            interfaceTypes.add(type.qualifiedTypeName());
            //type  接口也可能有父类
            readInterfaceTypes(interfaceTypes, type, processed);
        }

        if (classDoc.superclass() != null) {
            readInterfaceTypes(interfaceTypes, classDoc.superclass(), processed);
        }
    }

}
