package com.genx.javadoc;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/2 19:35
 */

public class ClassReadTest {


    /**
     * 检查重复类
     * @throws IOException
     */
    @Test
    public void test() throws IOException {

        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();

        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:/**/*.class");
        /*
        这里有个bug
        带空格的jar会被读取2次
        file:/D:/Program%20Files/Java/jdk1.8.0_211/jre/lib/rt.jar!/java/util/stream/Nodes$DoubleSpinedNodeBuilder.class
        file:D:\Program Files\Java\jdk1.8.0_211\jre\lib\rt.jar!/java/util/stream/Nodes$DoubleSpinedNodeBuilder.class
         */

        Map<String, Set<String>> map = new HashMap((int) (resources.length * 1.5 + 1));
        for (Resource r : resources) {
            MetadataReader reader = metadataReaderFactory.getMetadataReader(r);

            String className = reader.getClassMetadata().getClassName();

            if(className.startsWith("sun.") || className.startsWith("netscape.javascript.")){
                continue;
            }

            Set<String> list = map.get(className);
            if (list == null) {
                list = new HashSet(8);
                map.put(className, list);
            }

            String path = formatPath(r.getURL().getFile());
            list.add(path);
        }


        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.println(entry.getKey() + "\t" + StringUtils.join(entry.getValue(), "\t"));
            }
        }
    }

    /**
     *
     * jar:file:/D:/Program%20Files/Java/jdk1.8.0_211/jre/lib/rt.jar!/com/sun/corba/se/impl/interceptors/PINoOpHandlerImpl.class
     * jar:file:D:\Program Files\Java\jdk1.8.0_211\jre\lib\rt.jar!/com/sun/corba/se/impl/interceptors/PINoOpHandlerImpl.class
     * @return
     */
    private String formatPath(String path) {
        try {
            //TODO 路径无法统一 file:/ file:
            return URLDecoder.decode(path, "UTF-8").replace("\\", "/").replace("file:/", "file:");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
    }


    @Test
    public void test2() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class temp = classLoader.getClass();
        while (temp != null) {
            System.out.println(temp);

            temp = temp.getSuperclass();
        }

        //获取所有 classespath
        URL[] urls = ((URLClassLoader) classLoader).getURLs();

        for (URL url : urls) {
            System.out.println(url);
        }
    }

}
