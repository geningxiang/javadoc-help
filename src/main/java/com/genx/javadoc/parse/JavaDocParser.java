package com.genx.javadoc.parse;

import com.genx.javadoc.filter.IClassDocFilter;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/8 17:16
 */
public class JavaDocParser {

    private RootDoc rootDoc;

    public JavaDocParser(RootDoc rootDoc){
        this.rootDoc = rootDoc;
    }

    public void parse(){
        ClassDoc[] classes = rootDoc.classes();
        IClassDocFilter classDocFilter = classDoc -> {
            for (AnnotationDesc annotation : classDoc.annotations()) {
                if("org.springframework.web.bind.annotation.RestController".equals(annotation.annotationType().qualifiedTypeName())
                || "org.springframework.web.bind.annotation.Controller".equals(annotation.annotationType().qualifiedTypeName())){
                    return true;
                }
            }
            return false;
        };
        for (ClassDoc c : classes) {
            if(classDocFilter.accept(c)){
                parse(c);
            }
        }
    }

    public void parse(ClassDoc classDoc){
        for (MethodDoc methodDoc : classDoc.methods(false)) {
            parse(classDoc, methodDoc);
        }
    }

    public void parse(ClassDoc classDoc, MethodDoc methodDoc){
        System.out.println(classDoc.qualifiedTypeName() + "#" +  methodDoc.name());
    }
}
