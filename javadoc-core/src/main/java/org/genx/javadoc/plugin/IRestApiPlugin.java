package org.genx.javadoc.plugin;

import org.genx.javadoc.vo.JavaDocVO;
import org.genx.javadoc.vo.rest.RestInterfaceDoc;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/5 22:36
 */
public interface IRestApiPlugin {


    /**
     * 解析 Class 生成 Rest接口列表
     * @param javaDocVO
     * @return if null 尝试下一个插件
     */
    default List<RestInterfaceDoc> analysis(JavaDocVO javaDocVO) {
        return null;
    }

}
