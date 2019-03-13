package com.genx.javadoc.vo;

import com.genx.javadoc.utils.AnnotationUtil;
import com.sun.javadoc.Parameter;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 0:25
 */
public class ParamerVO {

    private String className;

    private String name;

    private String comment;

    private Map<String, AnnotationVO> annotations;

    public ParamerVO(Parameter parameter, String comment) {

        this.className = parameter.type().qualifiedTypeName();
        this.name = parameter.name();
        this.comment = comment;

        this.annotations = AnnotationUtil.readAnnotationMap(parameter.annotations());
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Map<String, AnnotationVO> getAnnotations() {
        return annotations;
    }
}
