package org.genx.javadoc.plugin.swagger;

import org.apache.commons.lang3.StringUtils;
import org.genx.javadoc.plugin.IJavaDocPlugin;
import org.genx.javadoc.vo.AnnotationDocVO;
import org.genx.javadoc.vo.ClassDocVO;
import org.genx.javadoc.vo.JavaDocVO;
import org.genx.javadoc.vo.TypeDoc;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/10 21:51
 */
public class SwaggerPlugin implements IJavaDocPlugin {


    private final String API_MODEL_PROPERTY = "io.swagger.annotations.ApiModelProperty";

    @Override
    public void handle(JavaDocVO javaDocVO) {
        for (ClassDocVO classDoc : javaDocVO.getClassDocs().values()) {
            handle(classDoc);
        }
    }

    public void handle(ClassDocVO classDoc) {
        AnnotationDocVO annotationDocVO;
        for (TypeDoc field : classDoc.getFields()) {
            annotationDocVO = field.getAnnotation(API_MODEL_PROPERTY);
            if (annotationDocVO != null) {

                String comment = annotationDocVO.getValue("value");

                boolean required = "true".equals(annotationDocVO.getValue("required"));

                if (StringUtils.isBlank(field.getComment())) {
                    field.setComment(comment);
                }

                if (required) {
                    field.addLimit("required");
                }
            }
        }

    }
}
