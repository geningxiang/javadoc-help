package org.genx.javadoc.utils;

import com.sun.javadoc.ClassDoc;

import java.util.*;

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
        readInterfaceTypes(interfaceTypes, classDoc);
        return interfaceTypes;
    }

    private static void readInterfaceTypes(Set<String> interfaceTypes, ClassDoc classDoc) {
        if (Object.class.getName().equals(classDoc.qualifiedTypeName())) {
            return;
        }
        List<ClassDoc> list = Arrays.asList(classDoc.interfaces());
        List<ClassDoc> nextList;
        while (!list.isEmpty()) {
            nextList = new LinkedList();
            for (ClassDoc type : list) {
                if (interfaceTypes.add(type.qualifiedTypeName())) {
                    nextList.add(type);
                }
            }
            list = nextList;
        }
        if (classDoc.superclass() != null) {
            readInterfaceTypes(interfaceTypes, classDoc.superclass());
        }
    }

}
