package com.genx.javadoc.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/22 0:18
 */
public class JavaDocUtil {

    public static List<File> listFiles(File dir, FileFilter fileFilter, boolean includeSubDirectories) {
        List<File> result = new ArrayList<>(1024);
        innerListFiles(result, dir, fileFilter, includeSubDirectories);
        return result;
    }

    /**
     * @param result                结果
     * @param directory             文件夹
     * @param fileFilter            过滤器
     * @param includeSubDirectories 是否包含文件夹
     */
    private static void innerListFiles(List<File> result, File directory, FileFilter fileFilter, boolean includeSubDirectories) {
        File[] files = directory.listFiles(fileFilter);
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (includeSubDirectories) {
                        result.add(file);
                    }
                    innerListFiles(result, file, fileFilter, includeSubDirectories);
                } else {
                    result.add(file);
                }
            }
        }
    }
}