package org.genx.javadoc.vo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/1 16:02
 */
public class RestApiDoc {

    /**
     * 各个环境的 基础地址
     */
    private Map<String, String> baseUrls = new HashMap(8);


    private List<RestInterfaceDoc> interfaces;


    public Map<String, String> getBaseUrls() {
        return baseUrls;
    }

    public void setBaseUrls(Map<String, String> baseUrls) {
        this.baseUrls = baseUrls;
    }

    public void setBaseUrl(String profile, String baseUrl) {
        this.baseUrls.put(profile, baseUrl);
    }


    public List<RestInterfaceDoc> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<RestInterfaceDoc> interfaces) {
        this.interfaces = interfaces;
    }
}
