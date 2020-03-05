package org.genx.javadoc;

import com.alibaba.fastjson.JSON;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import org.genx.javadoc.helper.RestApiBuilder;
import org.genx.javadoc.utils.TypeReader;
import org.genx.javadoc.vo.ClassDocVO;
import org.genx.javadoc.vo.RestApiDoc;
import org.genx.javadoc.vo.TypeDoc;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 18:27
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
        Map<String, ClassDocVO> map = JavaDocReader.read(sourceDirectory, compilePathList);


        System.out.println(JSON.toJSONString(map));


        RestApiDoc restApiDoc = new RestApiBuilder()
                .setBaseUrl("测试环境", "http://192.168.1.100:8080/")
                .setBaseUrl("预发布环境", "http://a.b.c/")
                .analysisClassDocs(map.values()).build();

        System.out.println(JSON.toJSONString(restApiDoc));

    }


    @Test
    public void test2() throws IOException {
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
        ClassDoc[] classDocs = JavaDocReader.readWithClassDocs(sourceDirectory, compilePathList);


        for (ClassDoc classDoc : classDocs) {

            if (!"com.genx.javadoc.controller.AppController".equals(classDoc.qualifiedTypeName())) {
                continue;
            }

            System.out.println(" == " + classDoc + " == ");


            for (MethodDoc method : classDoc.methods()) {

                System.out.println(" ## " + method + " ## ");


                System.out.println(method.returnType());

                TypeDoc typeDoc = TypeReader.read(method.returnType(), "", "", null);

                System.out.println(JSON.toJSONString(typeDoc));
            }


        }

    }


    @Test
    public void test3() throws IOException {

        List compilePathList = new ArrayList();

        compilePathList.add("E:\\idea-workspace\\CaimaoProject\\FinanceSimulate\\target\\classes;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-web\\2.2.2.RELEASE\\spring-boot-starter-web-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter\\2.2.2.RELEASE\\spring-boot-starter-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot\\2.2.2.RELEASE\\spring-boot-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-autoconfigure\\2.2.2.RELEASE\\spring-boot-autoconfigure-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-logging\\2.2.2.RELEASE\\spring-boot-starter-logging-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\ch\\qos\\logback\\logback-classic\\1.2.3\\logback-classic-1.2.3.jar;E:\\tools\\apache-maven-3.6.1\\repository\\ch\\qos\\logback\\logback-core\\1.2.3\\logback-core-1.2.3.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\logging\\log4j\\log4j-to-slf4j\\2.12.1\\log4j-to-slf4j-2.12.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\logging\\log4j\\log4j-api\\2.12.1\\log4j-api-2.12.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\slf4j\\jul-to-slf4j\\1.7.29\\jul-to-slf4j-1.7.29.jar;E:\\tools\\apache-maven-3.6.1\\repository\\jakarta\\annotation\\jakarta.annotation-api\\1.3.5\\jakarta.annotation-api-1.3.5.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-json\\2.2.2.RELEASE\\spring-boot-starter-json-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\fasterxml\\jackson\\datatype\\jackson-datatype-jdk8\\2.10.1\\jackson-datatype-jdk8-2.10.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\fasterxml\\jackson\\datatype\\jackson-datatype-jsr310\\2.10.1\\jackson-datatype-jsr310-2.10.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\fasterxml\\jackson\\module\\jackson-module-parameter-names\\2.10.1\\jackson-module-parameter-names-2.10.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-tomcat\\2.2.2.RELEASE\\spring-boot-starter-tomcat-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\tomcat\\embed\\tomcat-embed-core\\9.0.29\\tomcat-embed-core-9.0.29.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\tomcat\\embed\\tomcat-embed-el\\9.0.29\\tomcat-embed-el-9.0.29.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\tomcat\\embed\\tomcat-embed-websocket\\9.0.29\\tomcat-embed-websocket-9.0.29.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-validation\\2.2.2.RELEASE\\spring-boot-starter-validation-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\jakarta\\validation\\jakarta.validation-api\\2.0.1\\jakarta.validation-api-2.0.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\hibernate\\validator\\hibernate-validator\\6.0.18.Final\\hibernate-validator-6.0.18.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-web\\5.2.2.RELEASE\\spring-web-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-beans\\5.2.2.RELEASE\\spring-beans-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-webmvc\\5.2.2.RELEASE\\spring-webmvc-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-aop\\5.2.2.RELEASE\\spring-aop-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-expression\\5.2.2.RELEASE\\spring-expression-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-data-jpa\\2.2.2.RELEASE\\spring-boot-starter-data-jpa-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-aop\\2.2.2.RELEASE\\spring-boot-starter-aop-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\aspectj\\aspectjweaver\\1.9.5\\aspectjweaver-1.9.5.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-jdbc\\2.2.2.RELEASE\\spring-boot-starter-jdbc-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\zaxxer\\HikariCP\\3.4.1\\HikariCP-3.4.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-jdbc\\5.2.2.RELEASE\\spring-jdbc-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\jakarta\\activation\\jakarta.activation-api\\1.2.1\\jakarta.activation-api-1.2.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\jakarta\\persistence\\jakarta.persistence-api\\2.2.3\\jakarta.persistence-api-2.2.3.jar;E:\\tools\\apache-maven-3.6.1\\repository\\jakarta\\transaction\\jakarta.transaction-api\\1.3.3\\jakarta.transaction-api-1.3.3.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\hibernate\\hibernate-core\\5.4.9.Final\\hibernate-core-5.4.9.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\jboss\\logging\\jboss-logging\\3.4.1.Final\\jboss-logging-3.4.1.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\javassist\\javassist\\3.24.0-GA\\javassist-3.24.0-GA.jar;E:\\tools\\apache-maven-3.6.1\\repository\\net\\bytebuddy\\byte-buddy\\1.10.4\\byte-buddy-1.10.4.jar;E:\\tools\\apache-maven-3.6.1\\repository\\antlr\\antlr\\2.7.7\\antlr-2.7.7.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\jboss\\jandex\\2.1.1.Final\\jandex-2.1.1.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\dom4j\\dom4j\\2.1.1\\dom4j-2.1.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\hibernate\\common\\hibernate-commons-annotations\\5.1.0.Final\\hibernate-commons-annotations-5.1.0.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\glassfish\\jaxb\\jaxb-runtime\\2.3.2\\jaxb-runtime-2.3.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\glassfish\\jaxb\\txw2\\2.3.2\\txw2-2.3.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\sun\\istack\\istack-commons-runtime\\3.0.8\\istack-commons-runtime-3.0.8.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\jvnet\\staxex\\stax-ex\\1.8.1\\stax-ex-1.8.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\sun\\xml\\fastinfoset\\FastInfoset\\1.2.16\\FastInfoset-1.2.16.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\data\\spring-data-jpa\\2.2.3.RELEASE\\spring-data-jpa-2.2.3.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\data\\spring-data-commons\\2.2.3.RELEASE\\spring-data-commons-2.2.3.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-orm\\5.2.2.RELEASE\\spring-orm-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-aspects\\5.2.2.RELEASE\\spring-aspects-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\boot\\spring-boot-starter-data-redis\\2.2.2.RELEASE\\spring-boot-starter-data-redis-2.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\data\\spring-data-redis\\2.2.3.RELEASE\\spring-data-redis-2.2.3.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\data\\spring-data-keyvalue\\2.2.3.RELEASE\\spring-data-keyvalue-2.2.3.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-oxm\\5.2.2.RELEASE\\spring-oxm-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-context-support\\5.2.2.RELEASE\\spring-context-support-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\lettuce\\lettuce-core\\5.2.1.RELEASE\\lettuce-core-5.2.1.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\netty\\netty-common\\4.1.43.Final\\netty-common-4.1.43.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\netty\\netty-handler\\4.1.43.Final\\netty-handler-4.1.43.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\netty\\netty-buffer\\4.1.43.Final\\netty-buffer-4.1.43.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\netty\\netty-codec\\4.1.43.Final\\netty-codec-4.1.43.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\netty\\netty-transport\\4.1.43.Final\\netty-transport-4.1.43.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\netty\\netty-resolver\\4.1.43.Final\\netty-resolver-4.1.43.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\projectreactor\\reactor-core\\3.3.1.RELEASE\\reactor-core-3.3.1.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\reactivestreams\\reactive-streams\\1.0.3\\reactive-streams-1.0.3.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\kafka\\spring-kafka\\2.3.4.RELEASE\\spring-kafka-2.3.4.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\kafka\\kafka-clients\\2.3.1\\kafka-clients-2.3.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\github\\luben\\zstd-jni\\1.4.0-1\\zstd-jni-1.4.0-1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\lz4\\lz4-java\\1.6.0\\lz4-java-1.6.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\xerial\\snappy\\snappy-java\\1.1.7.3\\snappy-java-1.1.7.3.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\retry\\spring-retry\\1.2.4.RELEASE\\spring-retry-1.2.4.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-context\\5.2.2.RELEASE\\spring-context-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-messaging\\5.2.2.RELEASE\\spring-messaging-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-tx\\5.2.2.RELEASE\\spring-tx-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\commons\\commons-pool2\\2.5.0\\commons-pool2-2.5.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\jakarta\\xml\\bind\\jakarta.xml.bind-api\\2.3.2\\jakarta.xml.bind-api-2.3.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-core\\5.2.2.RELEASE\\spring-core-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\spring-jcl\\5.2.2.RELEASE\\spring-jcl-5.2.2.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\commons-io\\commons-io\\2.6\\commons-io-2.6.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\commons\\commons-lang3\\3.9\\commons-lang3-3.9.jar;E:\\tools\\apache-maven-3.6.1\\repository\\cn\\hutool\\hutool-poi\\5.1.0\\hutool-poi-5.1.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\cn\\hutool\\hutool-core\\5.1.0\\hutool-core-5.1.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\cn\\hutool\\hutool-log\\5.1.0\\hutool-log-5.1.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\poi\\poi-ooxml\\4.1.1\\poi-ooxml-4.1.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\poi\\poi\\4.1.1\\poi-4.1.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\commons\\commons-collections4\\4.4\\commons-collections4-4.4.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\commons\\commons-math3\\3.6.1\\commons-math3-3.6.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\poi\\poi-ooxml-schemas\\4.1.1\\poi-ooxml-schemas-4.1.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\xmlbeans\\xmlbeans\\3.1.0\\xmlbeans-3.1.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\commons\\commons-compress\\1.19\\commons-compress-1.19.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\github\\virtuald\\curvesapi\\1.06\\curvesapi-1.06.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\alibaba\\fastjson\\1.2.62\\fastjson-1.2.62.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\rocketmq\\rocketmq-client\\4.6.0\\rocketmq-client-4.6.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\rocketmq\\rocketmq-common\\4.6.0\\rocketmq-common-4.6.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\rocketmq\\rocketmq-remoting\\4.6.0\\rocketmq-remoting-4.6.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\netty\\netty-all\\4.1.43.Final\\netty-all-4.1.43.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\rocketmq\\rocketmq-logging\\4.6.0\\rocketmq-logging-4.6.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\netty\\netty-tcnative-boringssl-static\\2.0.28.Final\\netty-tcnative-boringssl-static-2.0.28.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\commons-validator\\commons-validator\\1.6\\commons-validator-1.6.jar;E:\\tools\\apache-maven-3.6.1\\repository\\commons-beanutils\\commons-beanutils\\1.9.2\\commons-beanutils-1.9.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\commons-digester\\commons-digester\\1.8.1\\commons-digester-1.8.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\commons-logging\\commons-logging\\1.2\\commons-logging-1.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\commons-collections\\commons-collections\\3.2.2\\commons-collections-3.2.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\apache\\rocketmq\\rocketmq-logappender\\4.6.0\\rocketmq-logappender-4.6.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\springfox\\springfox-swagger2\\2.9.2\\springfox-swagger2-2.9.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\springfox\\springfox-spi\\2.9.2\\springfox-spi-2.9.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\springfox\\springfox-core\\2.9.2\\springfox-core-2.9.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\springfox\\springfox-schema\\2.9.2\\springfox-schema-2.9.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\springfox\\springfox-swagger-common\\2.9.2\\springfox-swagger-common-2.9.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\springfox\\springfox-spring-web\\2.9.2\\springfox-spring-web-2.9.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\google\\guava\\guava\\20.0\\guava-20.0.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\fasterxml\\classmate\\1.5.1\\classmate-1.5.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\slf4j\\slf4j-api\\1.7.29\\slf4j-api-1.7.29.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\plugin\\spring-plugin-core\\1.2.0.RELEASE\\spring-plugin-core-1.2.0.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\springframework\\plugin\\spring-plugin-metadata\\1.2.0.RELEASE\\spring-plugin-metadata-1.2.0.RELEASE.jar;E:\\tools\\apache-maven-3.6.1\\repository\\org\\mapstruct\\mapstruct\\1.2.0.Final\\mapstruct-1.2.0.Final.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\springfox\\springfox-swagger-ui\\2.9.2\\springfox-swagger-ui-2.9.2.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\auth0\\java-jwt\\3.8.3\\java-jwt-3.8.3.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\fasterxml\\jackson\\core\\jackson-databind\\2.10.1\\jackson-databind-2.10.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\fasterxml\\jackson\\core\\jackson-core\\2.10.1\\jackson-core-2.10.1.jar;E:\\tools\\apache-maven-3.6.1\\repository\\commons-codec\\commons-codec\\1.13\\commons-codec-1.13.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\swagger\\swagger-annotations\\1.5.21\\swagger-annotations-1.5.21.jar;E:\\tools\\apache-maven-3.6.1\\repository\\io\\swagger\\swagger-models\\1.5.21\\swagger-models-1.5.21.jar;E:\\tools\\apache-maven-3.6.1\\repository\\com\\fasterxml\\jackson\\core\\jackson-annotations\\2.10.1\\jackson-annotations-2.10.1.jar");

        File sourceDirectory = new File("E:/idea-workspace/CaimaoProject/FinanceSimulate/src/main/java");
        Map<String, ClassDocVO> map = JavaDocReader.read(sourceDirectory, compilePathList);


        System.out.println(JSON.toJSONString(map));


        RestApiDoc restApiDoc = new RestApiBuilder()
                .setBaseUrl("测试环境", "http://192.168.1.100:8080/")
                .setBaseUrl("预发布环境", "http://a.b.c/")
                .analysisClassDocs(map.values()).build();

        System.out.println(JSON.toJSONString(restApiDoc));

        System.out.println(restApiDoc.getInterfaces().size());

    }


}