package org.genx.javadoc.plugin.springmvc;

import com.alibaba.fastjson.JSON;
import org.genx.javadoc.JavaDocReaderTest;
import org.genx.javadoc.bean.JavaDoc;
import org.genx.javadoc.bean.rest.RestApiDoc;
import org.genx.javadoc.bean.rest.RestInterfaceDoc;
import org.genx.javadoc.helper.RestApiBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/7 22:35
 */
public class SpringMVCPluginTest {

    @Test
    public void test() throws IOException {

        JavaDoc javaDoc = JavaDocReaderTest.read();


        RestApiDoc restApiDoc = new RestApiBuilder().analysisClassDocs(javaDoc).build();

        System.out.println("== 接口列表 ==");

        System.out.println(JSON.toJSONString(restApiDoc));


        Map<String, RestInterfaceDoc> map = restApiDoc.getInterfaces();


        System.out.println(JSON.toJSONString(map));


        System.out.println(JSON.toJSONString(map.get("/api/article/query")));

    }
}