package org.genx.javadoc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.genx.javadoc.bean.ClassDoc;
import org.genx.javadoc.bean.JavaDoc;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/5 9:11
 */
public class JavaDocReaderTest {

    @Test
    public void test() throws IOException {

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

        JavaDoc javaDoc = JavaDocReader.read(sourceDirectory, compilePathList);


        JSONArray array = new JSONArray(1024);
        for (ClassDoc value : javaDoc.getClassDocs().values()) {
            array.add(JSON.toJSON(value));
        }
        for (ClassDoc value : javaDoc.getIncludeClassDocs().values()) {
            array.add(JSON.toJSON(value));
        }

        System.out.println("==========================");

//        ClassDoc c1 = javaDoc.getClassDocs().get("org.genx.javadoc.controller.AppController");
//        System.out.println(JSON.toJSONString(c1));


        ClassDoc c2 = javaDoc.getClassDocs().get("org.genx.javadoc.test.JavaDocTest");
        System.out.println(JSON.toJSONString(c2));

        System.out.println("==== 类注释 ====");
        System.out.println(c2.getComment().toString());


        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("D:/work/javadoc/javadoc.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(javaDoc);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }

        IOUtils.write(array.toJSONString(), new FileOutputStream("D:/work/javadoc/javadoc.json"), Charsets.UTF_8);

    }

}