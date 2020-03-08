package org.genx.javadoc.utils;

import com.sun.javadoc.ClassDoc;
import org.genx.javadoc.plugin.JavaDocPluginManager;
import org.genx.javadoc.vo.ClassDocVO;
import org.genx.javadoc.vo.JavaDocVO;

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

    public JavaDocBuilder read(ClassDoc[] classes) {
        for (ClassDoc classDoc : classes) {
            env.classDocMap.put(classDoc.qualifiedTypeName(), classDoc);
        }
        return this;
    }

    public JavaDocVO build() {
        new ClassReader(env).read(env.classDocMap.values());
        JavaDocVO javaDocVO = new JavaDocVO();
        javaDocVO.setClassDocs(env.classDocs);
        javaDocVO.setIncludeClassDocs(env.includes);

        JavaDocPluginManager.handle(javaDocVO);

        return javaDocVO;
    }


    class JavaDocEnv {
        private Map<String, ClassDoc> classDocMap = new HashMap(2048);

        /**
         * 源码的类
         */
        private Map<String, ClassDocVO> classDocs = new HashMap(2048);

        /**
         * 外部引用
         */
        private Map<String, ClassDocVO> includes = new HashMap(2048);

        private boolean isBase(String className) {
            return classDocMap.containsKey(className);
        }

        public boolean exist(String className) {
            return classDocs.containsKey(className) || includes.containsKey(className);
        }

        public void add(ClassDocVO classDocVO) {
            if (isBase(classDocVO.getClassName())) {
                classDocs.put(classDocVO.getClassName(), classDocVO);
            } else {
                includes.put(classDocVO.getClassName(), classDocVO);
            }

        }

    }

}
