package org.genx.javadoc;

import org.genx.javadoc.utils.ClassReader;
import org.genx.javadoc.utils.FileUtil;
import org.genx.javadoc.vo.ClassDocVO;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import org.apache.commons.lang3.StringUtils;

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
        ClassDoc[] classes = root.classes();
        return new ClassReader().read(classes).getResult();
    }

    public synchronized static ClassDoc[] readWithClassDocs(File sourceDir, List<String> compilePaths) {
        javadocExecute(sourceDir, compilePaths);
        return root.classes();
    }


    private static void javadocExecute(File sourceDir, List<String> compilePaths) {
        List<String> commandList = new ArrayList<>(1024);
        commandList.add("-doclet");
        commandList.add(Doclet.class.getName());
        commandList.add("-encoding");
        commandList.add("utf-8");
        commandList.add("-classpath");
        commandList.add(StringUtils.join(compilePaths, ";"));

        Collection<File> list;
        if (sourceDir.isDirectory()) {
            //如果是文件夹 读取文件夹下的所有 .java 文件
            list = FileUtil.listFiles(sourceDir, file ->
                    file.isDirectory() || file.getName().toLowerCase().endsWith(".java"), false);
        } else {
            list = Arrays.asList(sourceDir);
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
