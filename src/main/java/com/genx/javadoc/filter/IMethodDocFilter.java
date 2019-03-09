package com.genx.javadoc.filter;

import com.sun.javadoc.MethodDoc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/8 17:28
 */
public interface IMethodDocFilter {

    boolean accept(MethodDoc methodDoc);
}
