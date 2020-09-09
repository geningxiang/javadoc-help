package org.genx.javadoc.plugin.swagger;

import org.genx.javadoc.bean.*;
import org.genx.javadoc.plugin.IJavaDocPlugin;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2020/3/10 21:51
 */
public class SwaggerPlugin implements IJavaDocPlugin {


    private final String API_MODEL_PROPERTY = "io.swagger.annotations.ApiModelProperty";

    private final String API_OPERATION = "io.swagger.annotations.ApiOperation";

    @Override
    public void handle(JavaDoc javaDocVO) {
        for (ClassDoc classDoc : javaDocVO.getClassDocs().values()) {
            handle(classDoc);
        }
    }

    public void handle(ClassDoc classDoc) {
        AnnotationDesc annotationDocVO;
        for (TypeDoc field : classDoc.getFields().values()) {
            annotationDocVO = field.getAnnotation(API_MODEL_PROPERTY);
            if (annotationDocVO != null) {

                String comment = annotationDocVO.getValue("value");

                boolean required = "true".equals(annotationDocVO.getValue("required"));

                if (field.getComment() == null) {
                    field.setComment(CommentDoc.of(comment));
                }

                if (required) {
                    field.addLimit("required");
                }
            }
        }

        for (MethodDoc method : classDoc.getMethods().values()) {
            annotationDocVO = method.getAnnotation(API_OPERATION);
            if (annotationDocVO != null) {
                String comment = annotationDocVO.getValue("value");
                if (method.getComment() == null) {
                    method.setComment(CommentDoc.of(comment));
                }
            }
        }

    }
}
