package org.genx.javadoc.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/4 22:23
 */
public class JavaDoc implements Serializable {

    /**
     * .java对应的类
     */
    private Map<String, ClassDoc> classDocs = new HashMap(2048);

    /**
     * 其他引用类
     */
    private Map<String, ClassDoc> includeClassDocs = new HashMap(2048);


    public Map<String, ClassDoc> getClassDocs() {
        return classDocs;
    }

    public ClassDoc getClassDoc(String className){
        ClassDoc doc = this.classDocs.get(className);
        if(doc == null){
            doc = this.includeClassDocs.get(className);
        }
        return doc;
    }


    public void setClassDocs(Map<String, ClassDoc> classDocs) {
        this.classDocs = classDocs;
    }

    public Map<String, ClassDoc> getIncludeClassDocs() {
        return includeClassDocs;
    }

    public void setIncludeClassDocs(Map<String, ClassDoc> includeClassDocs) {
        this.includeClassDocs = includeClassDocs;
    }
}
