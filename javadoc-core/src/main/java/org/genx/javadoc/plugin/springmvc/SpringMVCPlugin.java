package org.genx.javadoc.plugin.springmvc;

import org.apache.commons.lang3.StringUtils;
import org.genx.javadoc.bean.*;
import org.genx.javadoc.bean.rest.RestInterfaceDoc;
import org.genx.javadoc.bean.rest.RestNestTypeDoc;
import org.genx.javadoc.plugin.IRestApiPlugin;
import org.genx.javadoc.utils.DetailedTypeUtil;

import java.util.*;

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

    private static final String PATH_VARIABLE = "org.springframework.web.bind.annotation.PathVariable";

    private static final String REQUEST_HEADER = "org.springframework.web.bind.annotation.RequestHeader";

    private static final String REQUEST_BODY = "org.springframework.web.bind.annotation.RequestBody";

    private static final String REQUEST_PARAM = "org.springframework.web.bind.annotation.RequestParam";

    private static Map<String, String> METHOD_MAP = new HashMap(8);

    static {
        METHOD_MAP.put(REQUEST_MAPPING, null);
        METHOD_MAP.put("org.springframework.web.bind.annotation.GetMapping", "GET");
        METHOD_MAP.put("org.springframework.web.bind.annotation.PostMapping", "POST");
        METHOD_MAP.put("org.springframework.web.bind.annotation.PutMapping", "PUT");
        METHOD_MAP.put("org.springframework.web.bind.annotation.DeleteMapping", "DELETE");
        METHOD_MAP.put("org.springframework.web.bind.annotation.PatchMapping", "PATCH");
    }

    @Override
    public List<RestInterfaceDoc> analysis(JavaDoc javaDoc) {
        List<RestInterfaceDoc> result = new ArrayList(1024);
        List<RestInterfaceDoc> list;

        DetailedTypeUtil detailedTypeUtil = new DetailedTypeUtil(javaDoc);

        for (ClassDoc classDoc : javaDoc.getClassDocs().values()) {
            list = analysis(classDoc, detailedTypeUtil);
            if (list != null) {
                result.addAll(list);
            }
        }
        return result;
    }


    public List<RestInterfaceDoc> analysis(ClassDoc classDoc, DetailedTypeUtil detailedTypeUtil) {
        if (!classDoc.hasAnnotation(REST_CONTROLLER)
                && !classDoc.hasAnnotation(CONTROLLER)) {
            return null;
        }
        List<RestInterfaceDoc> list = new ArrayList(16);
        RestInterfaceDoc restInterfaceDoc;
        for (MethodDoc method : classDoc.getMethods().values()) {
            restInterfaceDoc = analysis(classDoc, method, detailedTypeUtil);
            if (restInterfaceDoc != null) {
                list.add(restInterfaceDoc);
            }
        }
        return list;


    }

    private RestInterfaceDoc analysis(ClassDoc classDoc, MethodDoc methodDoc, DetailedTypeUtil detailedTypeUtil) {
        if (isRequestMapping(classDoc, methodDoc)) {
            String[] paths = readPaths(classDoc, methodDoc);
            if (paths != null && paths.length > 0) {
                return analysis(paths, classDoc, methodDoc, detailedTypeUtil);
            }
        }
        return null;
    }

    private boolean isRequestMapping(ClassDoc classDoc, MethodDoc methodDoc) {
        if (classDoc.hasAnnotation(REST_CONTROLLER)
                || (classDoc.hasAnnotation(CONTROLLER) && methodDoc.hasAnnotation(RESPONSE_BODY))) {
            AnnotationDesc result;
            for (Map.Entry<String, String> entry : METHOD_MAP.entrySet()) {
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

    private RestInterfaceDoc analysis(String[] urls, ClassDoc classDocVO, MethodDoc methodDocVO, DetailedTypeUtil detailedTypeUtil) {
        RestInterfaceDoc doc = new RestInterfaceDoc();
        doc.setUrl(urls[0]);
        doc.setUrls(urls);
        doc.setMethods(readMethods(methodDocVO));
        doc.setProduces(readProduces(methodDocVO));
        doc.setName(readName(methodDocVO));
        doc.setDescription(methodDocVO.getComment());

        if (methodDocVO.getParams() != null && methodDocVO.getParams().size() > 0) {
            List<TypeDoc> methodParams = filterMethodParams(methodDocVO.getParams());

            for (TypeDoc param : methodParams) {

                RestNestTypeDoc restTypeDoc = detailedTypeUtil.analysis(param);

                if (param.hasAnnotation(PATH_VARIABLE)) {
                    doc.addPathVariable(restTypeDoc.getName(), restTypeDoc);
                } else if (param.hasAnnotation(REQUEST_HEADER)) {
                    doc.addHeader(restTypeDoc.getName(), restTypeDoc);
                } else if (param.hasAnnotation(REQUEST_BODY)) {
                    doc.addBody(restTypeDoc.getName(), restTypeDoc);
                } else {
                    doc.addParam(restTypeDoc.getName(), restTypeDoc);
                }
            }
        }

        doc.setReturnBody(detailedTypeUtil.analysis(methodDocVO.getReturnType()));
        if (doc.getReturnBody() != null) {
            //删除 method 返回的类型第一级的注释
            doc.getReturnBody().setComment(null);
        }
        doc.setReturnComment(methodDocVO.getReturnComment());
        return doc;
    }

    public String readName(MethodDoc methodDocVO) {
        AnnotationDesc annotationVO = methodDocVO.getAnnotation(REQUEST_MAPPING);
        if (annotationVO != null) {
            String[] names = annotationVO.getValues("name");
            if (names != null && names.length > 0) {
                return names[0];
            }
        }
        return "";
    }

    public String[] readMethods(MethodDoc methodDocVO) {
        AnnotationDesc annotationVO = methodDocVO.getAnnotation(REQUEST_MAPPING);
        Set<String> methods = new HashSet();
        if (annotationVO != null) {
            String[] values = annotationVO.getValues("method");
            if (values != null) {
                int index;
                for (int i = 0; i < values.length; i++) {
                    //org.springframework.web.bind.annotation.RequestMethod.GET 去掉前缀
                    if ((index = values[i].lastIndexOf(".")) > -1) {
                        methods.add(values[i].substring(index + 1));
                    }
                }
            }
        }
        for (Map.Entry<String, String> entry : METHOD_MAP.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue()) && methodDocVO.hasAnnotation(entry.getKey())) {
                methods.add(entry.getValue());
            }
        }
        return methods.toArray(new String[0]);
    }

    public String readProduces(MethodDoc methodDocVO) {
        AnnotationDesc annotationVO = methodDocVO.getAnnotation(REQUEST_MAPPING);
        if (annotationVO != null) {
            String[] values = annotationVO.getValues("produces");
            if (values != null) {
                return StringUtils.join(values, ";");
            }
        }
        return "";
    }


    private String[] readPaths(ClassDoc classDocVO, MethodDoc methodDocVO) {
        String[] classRestPaths = null;
        AnnotationDesc classRequestMapping = readRequestMapping(classDocVO);
        if (classRequestMapping != null) {
            classRestPaths = classRequestMapping.getValues("value");
        }

        AnnotationDesc methodRequestMapping = readRequestMapping(methodDocVO);

        String[] methodRestPaths = null;
        if (methodRequestMapping != null) {
            methodRestPaths = methodRequestMapping.getValues("value");
        }

        return composePath(classRestPaths, methodRestPaths);
    }

    private AnnotationDesc readRequestMapping(AbsDoc item) {
        LinkedHashMap<String, String> map = new LinkedHashMap(8);
        map.put(REQUEST_MAPPING, null);
        map.put("org.springframework.web.bind.annotation.GetMapping", "GET");
        map.put("org.springframework.web.bind.annotation.PostMapping", "POST");
        map.put("org.springframework.web.bind.annotation.PutMapping", "PUT");
        map.put("org.springframework.web.bind.annotation.DeleteMapping", "DELETE");
        map.put("org.springframework.web.bind.annotation.PatchMapping", "PATCH");
        AnnotationDesc result = null;
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
