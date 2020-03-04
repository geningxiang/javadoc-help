package com.genx.javadoc;

import com.alibaba.fastjson.JSON;
import com.genx.javadoc.helper.RestApiBuilder;
import com.genx.javadoc.utils.FileUtil;
import com.genx.javadoc.vo.ClassDocVO;
import com.genx.javadoc.vo.RestApiDoc;
import org.apache.commons.codec.digest.DigestUtils;

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

    public static void main(String[] args) throws IOException {

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


        Map<String, ClassDocVO> map = JavaDocReader.read(dir, compilePath);



        RestApiDoc restApiDoc = new RestApiBuilder()
                .analysisClassDocs(map.values()).build();


        File file3 = new File("E:\\github-workspace\\zheng\\doc/restApiData.js");
        FileUtil.writeFile(file3, "var restApiData = " + JSON.toJSON(restApiDoc) + ";");

    }

}
