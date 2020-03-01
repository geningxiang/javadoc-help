package com.genx.javadoc.helper;

import com.genx.javadoc.plugin.springmvc.SpringMVCPlugin;
import com.genx.javadoc.vo.ClassDocVO;
import com.genx.javadoc.vo.MethodDocVO;
import com.genx.javadoc.vo.RestApiDoc;
import com.genx.javadoc.vo.RestInterfaceDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/1 16:02
 */
public class RestApiBuilder {

    private final RestApiDoc restApiDoc;

    public RestApiBuilder() {
        restApiDoc = new RestApiDoc();
    }

    public RestApiBuilder setBaseUrl(String profile, String baseUrl) {
        this.restApiDoc.setBaseUrl(profile, baseUrl);
        return this;
    }

    public RestApiBuilder analysisClassDocs(Iterable<ClassDocVO> classDocVOList) {
        SpringMVCPlugin springMVCPlugin = new SpringMVCPlugin();
        List<RestInterfaceDoc> list = new ArrayList(1024);
        for (ClassDocVO classDocVO : classDocVOList) {
            for (MethodDocVO method : classDocVO.getMethods()) {
                List<RestInterfaceDoc> restList = springMVCPlugin.read(classDocVO, method);
                if(restList != null){
                    list.addAll(restList);
                }
            }
        }
        this.restApiDoc.setInterfaces(list);

        return this;
    }


    public RestApiDoc build() {
        return this.restApiDoc;
    }
}
