package org.genx.javadoc.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author: genx
 * @date: 2019/3/15 8:58
 */
public class ZipUtil {

    public static void unzip(InputStream in, File dir) {
        try {
            ZipInputStream zin = new ZipInputStream(in);
            try {
                ZipEntry ze;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = dir.getAbsolutePath() + File.separator + ze.getName();
                    if (ze.isDirectory()) {
                        File zipDir = new File(path);
                        if (!zipDir.exists()) {
                            zipDir.mkdirs();
                        }
                    } else {
                        int index = path.lastIndexOf("/");

                        if (index > -1) {
                            File zipDir = new File(path.substring(0, index));
                            if (!zipDir.exists()) {
                                //创建文件夹
                                zipDir.mkdirs();
                            }
                        }

                        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path, false));
                        try {
                            int read;
                            byte[] buffer = new byte[1024 * 10];
                            while ((read = zin.read(buffer, 0, buffer.length)) != -1) {
                                out.write(buffer, 0, read);
                            }
                            out.flush();
                            zin.closeEntry();
                        } finally {
                            out.close();
                        }
                    }
                }
            } finally {
                zin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
