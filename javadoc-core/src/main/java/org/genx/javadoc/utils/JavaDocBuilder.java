package org.genx.javadoc.utils;

import org.genx.javadoc.bean.ClassDoc;
import org.genx.javadoc.bean.JavaDoc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/6 22:57
 */
public class JavaDocBuilder {

    private JavaDocEnv env = new JavaDocEnv();


    public JavaDocBuilder(com.sun.javadoc.ClassDoc[] classDocs) {
        for (com.sun.javadoc.ClassDoc classDoc : classDocs) {
            env.classDocMap.put(classDoc.qualifiedTypeName(), classDoc);
        }

    }


    public JavaDoc build() {
        new ClassReader(env).read();

        JavaDoc javaDoc = new JavaDoc();
        javaDoc.setClassDocs(env.classDocs);
        javaDoc.setIncludeClassDocs(env.includes);

//        JavaDocPluginManager.handle(javaDocVO);

        return javaDoc;
    }


    class JavaDocEnv {
        private Map<String, com.sun.javadoc.ClassDoc> classDocMap = new HashMap(2048);

        /**
         * 源码的类
         */
        private Map<String, ClassDoc> classDocs = new HashMap(2048);

        /**
         * 外部引用
         */
        private Map<String, ClassDoc> includes = new HashMap(2048);

        public Map<String, com.sun.javadoc.ClassDoc> getClassDocMap() {
            return classDocMap;
        }

        private boolean isBase(String className) {
            return classDocMap.containsKey(className);
        }

        public ClassDoc get(String className) {
            ClassDoc classDoc = classDocs.get(className);
            if (classDoc == null) {
                classDoc = includes.get(className);
            }
            return classDoc;
        }

        public void add(ClassDoc classDoc) {
            if (isBase(classDoc.getClassName())) {
                classDocs.put(classDoc.getClassName(), classDoc);
            } else {
                includes.put(classDoc.getClassName(), classDoc);
            }

        }

    }

}
