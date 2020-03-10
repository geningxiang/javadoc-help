package org.genx.javadoc.third;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.genx.javadoc.JavaDocReader;
import org.genx.javadoc.helper.RestApiBuilder;
import org.genx.javadoc.utils.FileUtil;
import org.genx.javadoc.vo.JavaDocVO;
import org.genx.javadoc.vo.rest.RestApiDoc;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/5 0:30
 */
public class JavaDocBuildTest {

    @Test
    public void zhengBuild() throws IOException {
        File dir = new File("E:\\github-workspace\\zheng");

        Map<String, String> compilePathMap = new HashMap(1024);
        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(".jar")) {
                    try {
                        String sha256 = DigestUtils.sha256Hex(new FileInputStream(file.toFile()));
                        compilePathMap.put(sha256, file.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        List<String> compilePath = new ArrayList(compilePathMap.size());
        for (String value : compilePathMap.values()) {
            compilePath.add(value);
        }
        JavaDocVO javaDocVO = JavaDocReader.read(dir, compilePath);
        RestApiDoc restApiDoc = new RestApiBuilder()
                .analysisClassDocs(javaDocVO).build();
        File file3 = new File("E:\\github-workspace\\zheng\\doc/restApiData.js");
        FileUtil.writeFile(file3, "var restApiData = " + JSON.toJSONString(restApiDoc) + ";");
    }


    @Test
    public void ruoyiBuild() throws IOException {
        File dir = new File("E:\\github-workspace\\RuoYi");

        Map<String, String> compilePathMap = new HashMap(1024);
        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(".jar")) {
                    try {
                        String sha256 = DigestUtils.sha256Hex(new FileInputStream(file.toFile()));
                        compilePathMap.put(sha256, file.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        List<String> compilePath = new ArrayList(compilePathMap.size());
        for (String value : compilePathMap.values()) {
            compilePath.add(value);
        }
        JavaDocVO javaDocVO = JavaDocReader.read(dir, compilePath);
        RestApiDoc restApiDoc = new RestApiBuilder()
                .analysisClassDocs(javaDocVO).build();
        File file3 = new File("E:\\github-workspace\\RuoYi\\doc/restApiData.js");
        FileUtil.writeFile(file3, "var restApiData = " + JSON.toJSONString(restApiDoc) + ";");
    }

    @Test
    public void jeecgBuild() throws IOException {

        /*

        com.baomidou.mybatisplus.extension.plugins.pagination.Page
        17行 import org.jetbrains.annotations.Nullable;

         */


        File dir = new File("E:\\github-workspace\\jeecg-boot\\jeecg-boot\\jeecg-boot-module-system");

        Map<String, String> compilePathMap = new HashMap(1024);
        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(".jar")) {
                    try {
                        String sha256 = DigestUtils.sha256Hex(new FileInputStream(file.toFile()));
                        compilePathMap.put(sha256, file.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        List<String> compilePath = new ArrayList(compilePathMap.size());
        for (String value : compilePathMap.values()) {
            compilePath.add(value);
        }
        JavaDocVO javaDocVO = JavaDocReader.read(dir, compilePath);
        RestApiDoc restApiDoc = new RestApiBuilder()
                .analysisClassDocs(javaDocVO).build();
        File file3 = new File("E:\\github-workspace\\jeecg-boot\\jeecg-boot\\jeecg-boot-module-system/doc/restApiData.js");
        FileUtil.writeFile(file3, "var restApiData = " + JSON.toJSONString(restApiDoc) + ";");
    }
}
