package org.genx.javadoc.plugin.springmvc;

import org.apache.commons.lang3.StringUtils;
import org.genx.javadoc.plugin.IRestApiPlugin;
import org.genx.javadoc.vo.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 适用于 SpringMvc， SpringBoot
 * @author genx
 * @date 2020/3/1 16:08
 */
public class SpringMVCPlugin implements IRestApiPlugin {

    private static final String CONTROLLER = "org.springframework.stereotype.Controller";

    private static final String REST_CONTROLLER = "org.springframework.web.bind.annotation.RestController";

    private static final String RESPONSE_BODY = "org.springframework.web.bind.annotation.ResponseBody";


    private static final String REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";


    @Override
    public List<RestInterfaceDoc> analysis(ClassDocVO classDoc) {
        if (!classDoc.hasAnnotation(REST_CONTROLLER)
                && !classDoc.hasAnnotation(CONTROLLER)) {
            return null;
        }
        List<RestInterfaceDoc> list = new ArrayList(16);
        RestInterfaceDoc restInterfaceDoc;
        for (MethodDocVO method : classDoc.getMethods()) {
            restInterfaceDoc = analysis(classDoc, method);
            if (restInterfaceDoc != null) {
                list.add(restInterfaceDoc);
            }
        }
        return list;


    }

    private RestInterfaceDoc analysis(ClassDocVO classDoc, MethodDocVO methodDoc) {
        if (isRequestMapping(classDoc, methodDoc)) {
            String[] paths = readPaths(classDoc, methodDoc);
            if (paths != null && paths.length > 0) {
                return analysis(paths, classDoc, methodDoc);
            }
        }
        return null;
    }

    private boolean isRequestMapping(ClassDocVO classDoc, MethodDocVO methodDoc) {
        if (classDoc.hasAnnotation(REST_CONTROLLER)
                || (classDoc.hasAnnotation(CONTROLLER) && methodDoc.hasAnnotation(RESPONSE_BODY))) {
            LinkedHashMap<String, String> map = new LinkedHashMap(8);
            map.put(REQUEST_MAPPING, null);
            map.put("org.springframework.web.bind.annotation.GetMapping", "GET");
            map.put("org.springframework.web.bind.annotation.PostMapping", "POST");
            map.put("org.springframework.web.bind.annotation.PutMapping", "PUT");
            map.put("org.springframework.web.bind.annotation.DeleteMapping", "DELETE");
            map.put("org.springframework.web.bind.annotation.PatchMapping", "PATCH");
            AnnotationDocVO result;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                result = methodDoc.getAnnotation(entry.getKey());
                if (result != null) {
                    if (StringUtils.isNotBlank(entry.getValue())) {
                        //设置默认的
                        result.setValue("method", entry.getValue());
                    }
                    return true;
                }
            }
        }
        return false;
    }


    public List<TypeDoc> filterMethodParams(List<TypeDoc> params) {
        if (params == null) {
            return null;
        }
        List<TypeDoc> result = new ArrayList<>(params.size());
        for (TypeDoc param : params) {
            if (param.getClassName().startsWith("org.springframework.") || param.getClassName().startsWith("javax.servlet.")) {
                /*
                    org.springframework.validation.BindingResult
                    javax.servlet.http.HttpServletRequest
                    javax.servlet.http.HttpServletResponse
                 */
                continue;
            }
            result.add(param);
        }
        return result;
    }

    private RestInterfaceDoc analysis(String[] urls, ClassDocVO classDocVO, MethodDocVO methodDocVO) {
        RestInterfaceDoc doc = new RestInterfaceDoc();
        doc.setUrl(urls[0]);
        doc.setUrls(urls);
        doc.setMethods(readMethods(methodDocVO));
        doc.setProduces(readProduces(methodDocVO));
        doc.setName(readName(methodDocVO));
        doc.setDescription(methodDocVO.getComment());
        doc.setParams(filterMethodParams(methodDocVO.getParams()));
        doc.setReturnBody(methodDocVO.getReturnType());
        doc.setReturnComment(methodDocVO.getReturnComment());
        return doc;
    }

    public String readName(MethodDocVO methodDocVO) {
        AnnotationDocVO annotationVO = methodDocVO.getAnnotation(REQUEST_MAPPING);
        if (annotationVO != null) {
            String[] names = annotationVO.getValues("name");
            if (names != null && names.length > 0) {
                return names[0];
            }
        }
        return "";
    }

    public String[] readMethods(MethodDocVO methodDocVO) {
        AnnotationDocVO annotationVO = methodDocVO.getAnnotation(REQUEST_MAPPING);
        if (annotationVO != null) {
            String[] values = annotationVO.getValues("method");
            if (values != null) {
                int index;
                for (int i = 0; i < values.length; i++) {
                    //org.springframework.web.bind.annotation.RequestMethod.GET 去掉前缀
                    if ((index = values[i].lastIndexOf(".")) > -1) {
                        values[i] = values[i].substring(index + 1);
                    }
                }
                return values;
            }
        }
        return null;
    }

    public String readProduces(MethodDocVO methodDocVO) {
        AnnotationDocVO annotationVO = methodDocVO.getAnnotation(REQUEST_MAPPING);
        if (annotationVO != null) {
            String[] values = annotationVO.getValues("produces");
            if (values != null) {
                return StringUtils.join(values, ";");
            }
        }
        return "";
    }


    private String[] readPaths(ClassDocVO classDocVO, MethodDocVO methodDocVO) {
        String[] classRestPaths = null;
        AnnotationDocVO classRequestMapping = readRequestMapping(classDocVO);
        if (classRequestMapping != null) {
            classRestPaths = classRequestMapping.getValues("value");
        }

        AnnotationDocVO methodRequestMapping = readRequestMapping(methodDocVO);

        String[] methodRestPaths = null;
        if (methodRequestMapping != null) {
            methodRestPaths = methodRequestMapping.getValues("value");
        }

        return composePath(classRestPaths, methodRestPaths);
    }

    private AnnotationDocVO readRequestMapping(AbsDocVO item) {

        LinkedHashMap<String, String> map = new LinkedHashMap(8);
        map.put(REQUEST_MAPPING, null);
        map.put("org.springframework.web.bind.annotation.GetMapping", "GET");
        map.put("org.springframework.web.bind.annotation.PostMapping", "POST");
        map.put("org.springframework.web.bind.annotation.PutMapping", "PUT");
        map.put("org.springframework.web.bind.annotation.DeleteMapping", "DELETE");
        map.put("org.springframework.web.bind.annotation.PatchMapping", "PATCH");

        AnnotationDocVO result = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result = item.getAnnotation(entry.getKey());
            if (result != null) {
                if (StringUtils.isNotBlank(entry.getValue())) {
                    result.setValue("method", new String[]{entry.getValue()});
                }
                return result;
            }
        }
        return result;
    }


    private String[] composePath(String[] classRestPaths, String[] methodRestPaths) {
        if (methodRestPaths == null || methodRestPaths.length == 0) {
            return null;
        }
        if (classRestPaths == null || classRestPaths.length == 0) {
            return methodRestPaths;
        }
        String[] path = new String[classRestPaths.length * methodRestPaths.length];
        int i = 0;
        for (String classRestPath : classRestPaths) {
            for (String methodRestPath : methodRestPaths) {
                path[i++] = StringUtils.trimToEmpty(classRestPath) + StringUtils.trimToEmpty(methodRestPath);
            }
        }
        return path;
    }

}
