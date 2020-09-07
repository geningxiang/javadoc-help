package org.genx.javadoc.bean.rest;


import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/5 21:57
 */
public class RestApiDoc {

    private Map<String, String> baseUrls = new HashMap(8);

    private Map<String, RestInterfaceDoc> interfaces;

    public Map<String, String> getBaseUrls() {
        return baseUrls;
    }

    public void setBaseUrls(Map<String, String> baseUrls) {
        if (baseUrls != null) {
            this.baseUrls.putAll(baseUrls);
        }
    }

    public void setBaseUrl(String profile, String baseUrl) {
        this.baseUrls.put(profile, baseUrl);
    }

    public Map<String, RestInterfaceDoc> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Map<String, RestInterfaceDoc> interfaces) {
        this.interfaces = interfaces;
    }
}
