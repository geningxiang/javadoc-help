package com.genx.javadoc;

import com.alibaba.fastjson.JSON;
import com.genx.javadoc.vo.ClassDocVO;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 18:27
 */
public class JavaDocReaderTest {

    @Test
    public void read() throws IOException {

        Map<String, ClassDocVO> map = JavaDocReader.read(new File("E:\\idea_workspace\\javadoc-help\\javadoc-demo\\src\\main\\java"),
                new File("E:\\idea_workspace\\javadoc-help\\javadoc-demo\\target\\javadoc-demo\\WEB-INF\\classes"),
                new File("E:\\idea_workspace\\javadoc-help\\javadoc-demo\\target\\javadoc-demo\\WEB-INF\\lib"));

//        Map<String, ClassDocVO> map = JavaDocReader.read(
//                new File("D:\\idea-workspace\\CaimaoStockClient\\src\\main\\java"),
//                new File("D:\\idea-workspace\\CaimaoStockClient\\target\\caimao-stock-client\\WEB-INF\\classes"),
//                new File("D:\\idea-workspace\\CaimaoStockClient\\target\\caimao-stock-client\\WEB-INF\\lib"));




        Stream<ClassDocVO> ss =map.values().stream().filter(classDocVO ->
                classDocVO.getAnnotations().containsKey("org.springframework.web.bind.annotation.RestController")
                || classDocVO.getAnnotations().containsKey("org.springframework.stereotype.Controller"));

        List<ClassDocVO> classDocVOList = ss.collect(Collectors.toList());

        System.out.println(JSON.toJSONString(classDocVOList));

//        ResourceLoader resourceLoader = new DefaultResourceLoader();
//        Resource resource = resourceLoader.getResource("classpath:swagger.json");
//
//        String s = FileUtils.readFileToString(resource.getFile(), "UTF-8");
//
//        JSONObject json = JSON.parseObject(s);
//
//
//        System.out.println(json);

//        JSONObject paths = new JSONObject();
//
//        for (ClassDocVO classDocVO : classDocVOList) {
//            String baseUrl = "";
//            AnnotationVO annotationVO1 = classDocVO.getAnnotations().get("org.springframework.web.bind.annotation.RequestMapping");
//            if(annotationVO1 != null && annotationVO1.getData().get("value").length > 0){
//                baseUrl = annotationVO1.getData().get("value")[0];
//            }
//
//            for (MethodDocVO methodDocVO : classDocVO.getMethods()) {
//
//                AnnotationVO annotationVO = methodDocVO.getAnnotations().get("org.springframework.web.bind.annotation.RequestMapping");
//
//                if(annotationVO != null){
//                    String[] methods = annotationVO.getData().get("method");
//                    if(methods.length == 0){
//                        methods = new String[]{"get"};
//                    }
//
//
//                    for (String value : annotationVO.getData().get("value")) {
//
//                        JSONObject pathMap = new JSONObject();
//
//                        for (String method : methods) {
//
//                            JSONObject methodMap = new JSONObject();
//
//                            methodMap.put("summary", classDocVO.getClassName() + "#" + methodDocVO.getMethodName());
//                            methodMap.put("description", methodDocVO.getComment());
//
//                            methodMap.put("produces", new String[]{   "application/json"});
//
//                            JSONArray parameters = new JSONArray();
//
//
//                            for (Param param : methodDocVO.getParams()) {
//                                JSONObject parameter = new JSONObject();
//                                parameter.put("in", "body");
//                                parameter.put("name", param.getName());
//                                parameter.put("description", param.getComment() != null ? param.getComment() : " ");
//                                parameter.put("schema", new JSONObject());
//                                parameters.add(parameter);
//                            }
//
//
//                            methodMap.put("parameters", parameters);
//
//
//                            JSONObject responses = new JSONObject();
//
//                            JSONObject returnTest = new JSONObject();
//                            returnTest.put("description", methodDocVO.getReturnComment() != null ? methodDocVO.getReturnComment() : " ");
//
//                            responses.put("200", returnTest);
//
//                            methodMap.put("responses", responses);
//
////                            System.out.println(methodMap.toJSONString());
//
//                            pathMap.put(method, methodMap);
//
//
//                        }
//
//                        paths.put(baseUrl + value, pathMap);
//                    }
//                }
//            }
//        }
//
//        json.put("paths", paths);
//        System.out.println(json);
//
//        System.out.println(JSON.toJSONString(classDocVOList));

    }
}