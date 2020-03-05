package org.genx.javadoc.filter;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 10:56
 */
public interface IJavaDocFilter {

    boolean accept(ClassDoc classDoc);

    boolean accept(MethodDoc methodDoc);

}
