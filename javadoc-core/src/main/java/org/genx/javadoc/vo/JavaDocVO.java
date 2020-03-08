package org.genx.javadoc.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/6 22:53
 */
public class JavaDocVO {
    private Map<String, ClassDocVO> classDocs = new HashMap(2048);

    private Map<String, ClassDocVO> includeClassDocs = new HashMap(2048);


    public Map<String, ClassDocVO> getClassDocs() {
        return classDocs;
    }

    public void setClassDocs(Map<String, ClassDocVO> classDocs) {
        this.classDocs = classDocs;
    }

    public Map<String, ClassDocVO> getIncludeClassDocs() {
        return includeClassDocs;
    }

    public void setIncludeClassDocs(Map<String, ClassDocVO> includeClassDocs) {
        this.includeClassDocs = includeClassDocs;
    }

    public ClassDocVO getClassDoc(String className) {
        if (StringUtils.isBlank(className)) {
            return null;
        }
        ClassDocVO result = this.classDocs.get(className);
        if (result != null) {
            return result;
        }
        return this.includeClassDocs.get(className);
    }
}
