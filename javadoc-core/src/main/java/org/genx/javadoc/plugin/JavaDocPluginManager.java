package org.genx.javadoc.plugin;

import org.genx.javadoc.bean.JavaDoc;
import org.genx.javadoc.plugin.jsr.ValidationPlugin;
import org.genx.javadoc.plugin.lombok.LomBokPlugin;
import org.genx.javadoc.plugin.swagger.SwaggerPlugin;

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

        // LomBok 插件 扫描 LomBok相关注解 补上 get set 方法
        list.add(new LomBokPlugin());

        // jsr 插件  读取字段的限制
        list.add(new ValidationPlugin());

        // swagger 插件 读取 swagger注解上的信息
        list.add(new SwaggerPlugin());
    }

    public static void handle(JavaDoc javaDocVO) {
        for (IJavaDocPlugin javaDocPlugin : list) {
            javaDocPlugin.handle(javaDocVO);
        }
    }
}
