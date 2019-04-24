package com.genx.javadoc;

import com.genx.javadoc.utils.FileUtil;
import com.genx.javadoc.utils.StringUtils;
import com.genx.javadoc.vo.ClassDocVO;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 16:02
 */
public class JavaDocReader {

    private static RootDoc root = null;

    public static class Doclet {
        public static LanguageVersion languageVersion() {
            return LanguageVersion.JAVA_1_5;
        }

        public static boolean start(RootDoc root) {
            JavaDocReader.root = root;
            return true;
        }
    }

    public synchronized static Map<String, ClassDocVO> read(File sourceDir, List<String> compilePaths) {
        javadocExecute(sourceDir, compilePaths);
        Map<String, ClassDocVO> map = new HashMap(1024);
        ClassDoc[] classes = root.classes();
        for (ClassDoc c : classes) {
            ClassDocVO classDocVO = new ClassDocVO(c);
            map.put(classDocVO.getClassName(), classDocVO);
        }
        return map;

    }

    private static void javadocExecute(File sourceDir, List<String> compilePaths) {
//        List<String> classPathes = new ArrayList(1024);
////        classPathes.add("D:\\Program Files\\Java\\jdk1.8.0\\lib/tools.jar");
//        classPathes.add(classesDir.getAbsolutePath());
//        Collection<File> jarList = FileUtil.listFiles(libDir, file -> file.isDirectory() || file.getName().toLowerCase().endsWith(".jar"), false);
//        for (File jar : jarList) {
//            classPathes.add(jar.getAbsolutePath());
//        }

        List<String> commandList = new ArrayList<>(1024);
        commandList.add("-doclet");
        commandList.add(Doclet.class.getName());
        commandList.add("-encoding");
        commandList.add("utf-8");
        commandList.add("-classpath");
        commandList.add(StringUtils.join(compilePaths, ";"));

        Collection<File> list = FileUtil.listFiles(sourceDir, file ->
                file.isDirectory() || file.getName().toLowerCase().endsWith(".java"), false);

        if (list.size() == 0) {
            throw new IllegalArgumentException("list is null");
        }

        for (File file : list) {
            commandList.add(file.getAbsolutePath());
        }
        System.out.println("#### javadoc command start ####");
        System.out.println(StringUtils.join(commandList, System.lineSeparator()));
        System.out.println("#### javadoc command end ####");
        com.sun.tools.javadoc.Main.execute(commandList.toArray(new String[commandList.size()]));
    }
}
