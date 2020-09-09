package org.genx.javadoc.javaparser;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/7/8 23:13
 */
public class ParseUtils {

    public static Set<String> packageSet(Map<String, JSONObject> map){
        Set<String> set = new HashSet();
        for (JSONObject value : map.values()) {
            set.add(StringUtils.trimToEmpty(value.getString("packageName")));
        }
        return set;
    }

}
