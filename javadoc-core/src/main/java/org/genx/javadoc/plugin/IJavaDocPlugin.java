package org.genx.javadoc.plugin;

import org.genx.javadoc.vo.ClassDocVO;
import org.genx.javadoc.vo.JavaDocVO;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/1 16:09
 */
public interface IJavaDocPlugin {

    /**
     * 用于调整优先级
     * @return
     */
    default int getOrder() {
        return 0;
    }

    void handle(JavaDocVO javaDocVO);

}
