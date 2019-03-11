package com.genx.javadoc;

import com.genx.javadoc.filter.IJavaDocFilter;
import com.genx.javadoc.utils.JavaDocUtil;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 18:27
 */
public class JavaDocReaderTest {

    @Test
    public void read() {

        IJavaDocFilter classDocFilter = new IJavaDocFilter() {
            @Override
            public boolean accept(ClassDoc classDoc) {
                return JavaDocUtil.hasAnnotation(classDoc, "org.springframework.web.bind.annotation.RestController")
                        || JavaDocUtil.hasAnnotation(classDoc, "org.springframework.stereotype.Controller");
            }

            @Override
            public boolean accept(MethodDoc methodDoc) {
                return JavaDocUtil.hasAnnotation(methodDoc, "org.springframework.web.bind.annotation.RequestMapping");
            }
        };

        JavaDocReader.read(new File("E:\\idea_workspace\\javadoc-help\\javadoc-demo\\src\\main\\java"),
                new File("E:\\idea_workspace\\javadoc-help\\javadoc-demo\\target\\javadoc-demo\\WEB-INF\\classes"),
                new File("E:\\idea_workspace\\javadoc-help\\javadoc-demo\\target\\javadoc-demo\\WEB-INF\\lib"),
                classDocFilter);
    }
}