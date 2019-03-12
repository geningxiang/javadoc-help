package com.genx.javadoc.vo;

import com.genx.javadoc.utils.AnnotationUtil;
import com.sun.deploy.util.StringUtils;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:32
 */
public class ClassDocVO {

    private String className;

    private String rawCommentText;

    private Map<String, AnnotationVO> annotationMap;

    private List<MethodDocVO> methods;

    public ClassDocVO(ClassDoc classDoc) {
        this.className = classDoc.qualifiedTypeName();
        this.rawCommentText = classDoc.getRawCommentText();

        this.annotationMap = AnnotationUtil.readAnnotationMap(classDoc);

        MethodDoc[] methodDocs = classDoc.methods(false);
        this.methods = new ArrayList<>(methodDocs.length);
        for (MethodDoc methodDoc : methodDocs) {
            this.methods.add(new MethodDocVO(methodDoc));
        }
    }

    public String getClassName() {
        return className;
    }

    public String getRawCommentText() {
        return rawCommentText;
    }

    public Map<String, AnnotationVO> getAnnotationMap() {
        return annotationMap;
    }

    public List<MethodDocVO> getMethods() {
        return methods;
    }
}
