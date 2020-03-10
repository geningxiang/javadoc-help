package org.genx.javadoc.plugin;

import org.genx.javadoc.plugin.jsr.ValidationPlugin;
import org.genx.javadoc.plugin.lombok.LomBokPlugin;
import org.genx.javadoc.plugin.swagger.SwaggerPlugin;
import org.genx.javadoc.vo.JavaDocVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/7 22:01
 */
public class JavaDocPluginManager {

    private static List<IJavaDocPlugin> list = new ArrayList(8);

    static {
        //TODO 后续考虑配置化  从xml读取
        list.add(new LomBokPlugin());
        list.add(new ValidationPlugin());
        list.add(new SwaggerPlugin());
    }

    public static void handle(JavaDocVO javaDocVO) {
        for (IJavaDocPlugin javaDocPlugin : list) {
            javaDocPlugin.handle(javaDocVO);
        }
    }
}
