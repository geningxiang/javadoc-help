package com.genx.javadoc.utils;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/14 0:15
 */
public class StringUtils {
    public static String join(Collection<?> collection, String separator) {
        StringBuilder s = new StringBuilder(1024);
        if (collection != null) {
            int i = 0;
            for (Object item : collection) {
                if (i > 0 && separator != null) {
                    s.append(separator);
                }
                s.append(String.valueOf(item));
                i++;
            }
        }
        return s.toString();
    }
}
