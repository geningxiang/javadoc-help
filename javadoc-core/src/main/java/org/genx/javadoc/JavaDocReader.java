package org.genx.javadoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import org.apache.commons.lang3.StringUtils;
import org.genx.javadoc.utils.FileUtil;
import org.genx.javadoc.utils.JavaDocBuilder;
import org.genx.javadoc.vo.JavaDocVO;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public synchronized static JavaDocVO read(File sourceDir, List<String> compilePaths) {
        return read(Arrays.asList(sourceDir), compilePaths);
    }

    public synchronized static JavaDocVO read(List<File> sourceDirs, List<String> compilePaths) {
        ClassDoc[] classes = readWithClassDocs(sourceDirs, compilePaths);
        return new JavaDocBuilder().read(classes).build();
    }


    public synchronized static ClassDoc[] readWithClassDocs(List<File> sourceDirs, List<String> compilePaths) {
        javadocExecute(sourceDirs, compilePaths);
        return root.classes();
    }


    private static void javadocExecute(List<File> sourceDirs, List<String> compilePaths) {
        List<String> commandList = new ArrayList<>(2048);
        commandList.add("-doclet");
        commandList.add(Doclet.class.getName());
        commandList.add("-encoding");
        commandList.add("utf-8");
        commandList.add("-classpath");
        commandList.add(StringUtils.join(compilePaths, ";"));

        List<File> list = new ArrayList(1024);
        for (File sourceDir : sourceDirs) {
            if (sourceDir.exists()) {
                if (sourceDir.isDirectory()) {
                    //如果是文件夹 读取文件夹下的所有 .java 文件
                    list.addAll(FileUtil.listFiles(sourceDir, file ->
                            file.isDirectory() || file.getName().toLowerCase().endsWith(".java"), false));
                } else if (sourceDir.getName().toLowerCase().endsWith(".java")) {
                    list.add(sourceDir);
                }
            }

        }

        if (list.size() == 0) {
            throw new IllegalArgumentException("源文件不能为空");
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
