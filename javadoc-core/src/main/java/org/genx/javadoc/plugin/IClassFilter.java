package org.genx.javadoc.plugin;

import org.genx.javadoc.vo.ClassDocVO;
import org.genx.javadoc.vo.MethodDocVO;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/1 16:09
 */
public interface IClassFilter {

    boolean matchClass(ClassDocVO classDocVO);

    boolean matchMethod(ClassDocVO classDocVO, MethodDocVO methodDocVO);

}
