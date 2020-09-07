package org.genx.javadoc.plugin.jsr;

import com.alibaba.fastjson.JSON;
import org.genx.javadoc.JavaDocReaderTest;
import org.genx.javadoc.bean.ClassDoc;
import org.genx.javadoc.bean.JavaDoc;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/6 14:14
 */
public class ValidationPluginTest {

    @Test
    public void test() throws IOException {
        JavaDoc javaDoc = JavaDocReaderTest.read();

        ClassDoc user = javaDoc.getClassDocs().get("org.genx.javadoc.entity.User");
        System.out.println(JSON.toJSONString(user));


        ClassDoc userController = javaDoc.getClassDocs().get("org.genx.javadoc.controller.UserController");
        System.out.println(JSON.toJSONString(userController));

    }

}