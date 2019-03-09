package com.genx.javadoc.filter;

import com.sun.javadoc.ClassDoc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/8 17:20
 */
public interface IClassDocFilter {

    boolean accept(ClassDoc classDoc);
}
