package org.genx.javadoc.helper;

import org.genx.javadoc.plugin.IRestApiPlugin;
import org.genx.javadoc.vo.ClassDocVO;
import org.genx.javadoc.vo.RestApiDoc;
import org.genx.javadoc.vo.RestInterfaceDoc;
import org.genx.javadoc.plugin.springmvc.SpringMVCPlugin;

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

    private static List<IRestApiPlugin> restApiPlugins = new ArrayList(8);

    static {
        //TODO 后续考虑 用Spring管理 或者放到xml中
        restApiPlugins.add(new SpringMVCPlugin());
    }

    public RestApiBuilder() {
        restApiDoc = new RestApiDoc();
    }

    public RestApiBuilder setBaseUrl(String profile, String baseUrl) {
        this.restApiDoc.setBaseUrl(profile, baseUrl);
        return this;
    }

    public RestApiBuilder appendPlugin(IRestApiPlugin restApiPlugin) {
        if (restApiPlugin != null) {
            restApiPlugins.add(restApiPlugin);
        }
        return this;
    }

    public RestApiBuilder analysisClassDocs(Iterable<ClassDocVO> classDocVOList) {
        List<RestInterfaceDoc> list = new ArrayList(1024);
        List<RestInterfaceDoc> temp;
        for (ClassDocVO classDocVO : classDocVOList) {

            for (IRestApiPlugin restApiPlugin : restApiPlugins) {
                temp = restApiPlugin.analysis(classDocVO);
                if (temp != null) {
                    list.addAll(temp);
                    break;
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
