package org.genx.javadoc;

import com.sun.javadoc.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/4 20:32
 */
public class NoteTest {

    public static void main(String[] args) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/");

        String path = resource.getFile().getAbsolutePath();

        List compilePathList = new ArrayList();
        compilePathList.add(path + "/../../../javadoc-demo/target/javadoc-demo/WEB-INF/classes");

        File libDir = new File(path + "/../../../javadoc-demo/target/javadoc-demo/WEB-INF/lib/");
        for (File file : libDir.listFiles()) {
            compilePathList.add(file.getAbsolutePath());
        }

        File sourceDirectory = new File(path + "/../../../javadoc-demo/src/main/java/");

        ClassDoc[] classDocs = JavaDocReader.readWithClassDocs(Arrays.asList(sourceDirectory), compilePathList);

        System.out.println("################");

        for (ClassDoc classDoc : classDocs) {
            if("AppController".equals(classDoc.name())){

            }
        }


    }
}
