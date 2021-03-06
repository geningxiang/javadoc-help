package org.genx.javadoc.plugin.lombok;

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
 * @date 2020/3/6 22:36
 */
public class LomBokPluginTest {

    @Test
    public void test() throws IOException {

        JavaDoc javaDoc = JavaDocReaderTest.read();

        ClassDoc user = javaDoc.getClassDocs().get("org.genx.javadoc.entity.User");
        System.out.println(JSON.toJSONString(user));


    }

}