package com.genx.javadoc.test;

import com.sun.deploy.util.StringUtils;
import com.sun.javadoc.*;
import sun.reflect.generics.tree.ReturnType;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/22 0:09
 */
public class JavaDocReader {
    private static RootDoc root;

    // 一个简单Doclet,收到 RootDoc对象保存起来供后续使用
    // 参见参考资料6
    public static class Doclet {

        public Doclet() {
        }

        public static LanguageVersion languageVersion() {
            System.out.println("on languageVersion");
            return LanguageVersion.JAVA_1_5;
        }

        public static boolean start(RootDoc root) {
            JavaDocReader.root = root;
            return true;
        }
    }

    // 显示DocRoot中的基本信息
    public static void show() {
        ClassDoc[] classes = root.classes();
        for (int i = 0; i < classes.length; ++i) {

            if (!"com.automate.controller.api.ContainerController".equals(classes[i].qualifiedTypeName())) {
                continue;
            }

            System.out.println("=======================================");
            System.out.println(classes[i].qualifiedTypeName());

            System.out.println(classes[i].commentText());

            for (Tag tag : classes[i].tags()) {
                System.out.println("[tag]" + tag.name() + " :" + tag.text());
            }

            System.out.println("==================构造函数=====================");
            for (ConstructorDoc constructor : classes[i].constructors()) {
                System.out.println(constructor.name());
            }

            System.out.println("==================字段=====================");
            for (FieldDoc fieldDoc : classes[i].fields(false)) {
                System.out.println(fieldDoc.name());
            }

            System.out.println("==================序列化字段=====================");
            /**
             * 如果继承 implements Serializable
             * 返回哪些字段会被序列化
             */
            for (FieldDoc fieldDoc : classes[i].serializableFields()) {
                System.out.println(fieldDoc.name());
            }


            System.out.println("==================方法=====================");
            for (MethodDoc method : classes[i].methods(false)) {
                System.out.println("####################");
                System.out.println(method.name());

//                System.out.printf("\t%s\n", method.getRawCommentText());


                for (ParamTag paramTag : method.paramTags()) {
                    System.out.println(paramTag.parameterName() + "  | " + paramTag.parameterComment());
                }


                for (AnnotationDesc annotation : method.annotations()) {
                    System.out.println("[注解]" + annotation.annotationType().qualifiedName());


                    for (AnnotationDesc.ElementValuePair pair : annotation.elementValues()) {
                        System.out.println(pair.element().name());

                        Object o = pair.value().value();

                        if (o.getClass().isArray()) {
                            Object[] array = (Object[]) o;
                            for (Object obj : array) {
                                System.out.println(obj);
                            }
                        }
                    }
                }


                for (Parameter parameter : method.parameters()) {

                    System.out.println("[参数]" + parameter.type().qualifiedTypeName() + " " + parameter.name());
                }

                Type returnType = method.returnType();


                ParameterizedType t = returnType.asParameterizedType();
                if (t != null) {
                    System.out.println("----- 泛型 -----");
                    ClassDoc c = t.asClassDoc();

                    System.out.println(c);

                    for (TypeVariable typeVariable : c.typeParameters()) {
                        System.out.println(typeVariable.qualifiedTypeName());
                    }

                    for (FieldDoc fieldDoc : c.fields(false)) {
                        System.out.println(fieldDoc.type().qualifiedTypeName() + " " + fieldDoc.name());
                    }


                    System.out.println(t.toString());

                    for (Type type : t.typeArguments()) {
                        System.out.println("[typeArguments]" + type.qualifiedTypeName());
                    }
                }

                System.out.println("return " + returnType.qualifiedTypeName());

            }


        }
    }

    public static RootDoc getRoot() {
        return root;
    }

    public JavaDocReader() {

    }

    public static void main(final String... args) throws Exception {
        // 调用com.sun.tools.javadoc.Main执行javadoc,参见 参考资料3
        // javadoc的调用参数，参见 参考资料1
        // -doclet 指定自己的docLet类名
        // -classpath 参数指定 源码文件及依赖库的class位置，不提供也可以执行，但无法获取到完整的注释信息(比如annotation)
        // -encoding 指定源码文件的编码格式


        List<String> classPathes = new ArrayList(1024);

        classPathes.add("D:\\Program Files\\Java\\jdk1.8.0\\lib/tools.jar");

        classPathes.add("D:\\github-workspace\\Automate2\\automate-web\\target\\Automate2\\WEB-INF\\classes");

        File libDir = new File("D:\\github-workspace\\Automate2\\automate-web\\target\\Automate2\\WEB-INF\\lib");
        Collection<File> jarList = JavaDocUtil.listFiles(libDir, pathname -> pathname.getName().toLowerCase().endsWith(".jar"), false);

        for (File file : jarList) {
            classPathes.add(file.getAbsolutePath());
        }


//        File sourceDir = new File("D:\\idea-workspace\\CaimaoQuotation\\QuotationServer\\src\\main\\java");
        File sourceDir = new File("D:\\github-workspace\\Automate2\\automate-web\\src\\main\\java\\com\\automate\\controller\\api");


        System.out.println(sourceDir.getAbsolutePath());

        Collection<File> list = JavaDocUtil.listFiles(sourceDir, file ->
                file.isDirectory() || file.getName().toLowerCase().endsWith(".java"), false);


        List<String> commandList = new ArrayList<>(1024);
        commandList.add("-doclet");
        commandList.add(Doclet.class.getName());
        commandList.add("-encoding");
        commandList.add("utf-8");
        commandList.add("-classpath");
        commandList.add(StringUtils.join(classPathes, ";"));

        if (list.size() == 0) {
            throw new IllegalArgumentException("list is null");
        }

        for (File file : list) {
            commandList.add(file.getAbsolutePath());
        }

        System.out.println(StringUtils.join(commandList, " "));


        com.sun.tools.javadoc.Main.execute(commandList.toArray(new String[commandList.size()]));

        show();

    }
}