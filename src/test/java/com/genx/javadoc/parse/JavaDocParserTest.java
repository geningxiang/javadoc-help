package com.genx.javadoc.parse;

import com.genx.javadoc.test.JavaDocReader;
import com.genx.javadoc.test.JavaDocUtil;
import com.sun.deploy.util.StringUtils;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/8 17:24
 */
public class JavaDocParserTest {
    private static RootDoc root;

    // 一个简单Doclet,收到 RootDoc对象保存起来供后续使用
    // 参见参考资料6
    public static class Doclet {

        public Doclet() {
        }

        public static LanguageVersion languageVersion() {
            System.out.println("on languageVersion");
            return LanguageVersion.JAVA_1_5;
        }

        public static boolean start(RootDoc root) {
            JavaDocParserTest.root = root;
            return true;
        }
    }

    public static void main(String[] args) {

        // 调用com.sun.tools.javadoc.Main执行javadoc,参见 参考资料3
        // javadoc的调用参数，参见 参考资料1
        // -doclet 指定自己的docLet类名
        // -classpath 参数指定 源码文件及依赖库的class位置，不提供也可以执行，但无法获取到完整的注释信息(比如annotation)
        // -encoding 指定源码文件的编码格式


        List<String> classPathes = new ArrayList(1024);

        classPathes.add("D:\\Program Files\\Java\\jdk1.8.0\\lib/tools.jar");

        classPathes.add("D:\\github-workspace\\Automate2\\automate-web\\target\\Automate2\\WEB-INF\\classes");

        File libDir = new File("D:\\github-workspace\\Automate2\\automate-web\\target\\Automate2\\WEB-INF\\lib");
        Collection<File> jarList = JavaDocUtil.listFiles(libDir, pathname -> pathname.getName().toLowerCase().endsWith(".jar"), false);

        for (File file : jarList) {
            classPathes.add(file.getAbsolutePath());
        }

        File sourceDir = new File("D:\\github-workspace\\Automate2\\automate-web\\src\\main\\java");


        Collection<File> list = JavaDocUtil.listFiles(sourceDir, file ->
                file.isDirectory() || file.getName().toLowerCase().endsWith(".java"), false);


        List<String> commandList = new ArrayList<>(1024);
        commandList.add("-doclet");
        commandList.add(JavaDocParserTest.Doclet.class.getName());
        commandList.add("-encoding");
        commandList.add("utf-8");
        commandList.add("-classpath");
        commandList.add(StringUtils.join(classPathes, ";"));

        if (list.size() == 0) {
            throw new IllegalArgumentException("list is null");
        }

        for (File file : list) {
            commandList.add(file.getAbsolutePath());
        }


        com.sun.tools.javadoc.Main.execute(commandList.toArray(new String[commandList.size()]));


        System.out.println(root);

        JavaDocParser javaDocParser = new JavaDocParser(root);

        javaDocParser.parse();
    }

}