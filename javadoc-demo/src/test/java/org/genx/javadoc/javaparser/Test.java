package org.genx.javadoc.javaparser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Test {

    private static File jsonFile = new File("E:/2020年07月/sports/xwork.json");

    private static String sourcePath = "E:/idea-workspace/CaimaoSportsForGit/src/";

    private static Map<String, String> actionReturnMap = new HashMap();

    static {
        actionReturnMap.put(" SUCCESS", " \"success\"");
        actionReturnMap.put(" NONE", " \"none\"");
        actionReturnMap.put(" ERROR", " \"error\"");
        actionReturnMap.put(" INPUT", " \"input\"");
        actionReturnMap.put(" LOGIN ", " \"login\"");
    }

    public static void main(String[] args) throws Exception {

        byte[] data = IOUtils.readFully(new FileInputStream(jsonFile), (int) jsonFile.length());
        JSONArray array = JSONArray.parseArray(new String(data));

        Map<String, Map<String, JSONObject>> map = new HashMap();
        Map<String, JSONObject> temp;
        JSONObject item;
        for (int i = 0; i < array.size(); i++) {
            item = array.getJSONObject(i);
            temp = map.get(item.getString("actionCls"));
            if (temp == null) {
                temp = new HashMap();
                map.put(item.getString("actionCls"), temp);
            }
            String method = item.getString("method");
            if (StringUtils.isBlank(method)) {
                method = "execute";
            }
            temp.put(method, item);
        }

        for (Map.Entry<String, Map<String, JSONObject>> stringMapEntry : map.entrySet()) {
            System.out.println(stringMapEntry.getKey());

            resolve(stringMapEntry.getKey(), stringMapEntry.getValue());

        }


    }

    private static void resolve(String className, Map<String, JSONObject> actionMap) throws IOException {
        String classPath = className.replace(".", "/") + ".java";
        File file = new File(sourcePath + classPath);
        // parse the file
        ParseResult<CompilationUnit> cu = new JavaParser().parse(new FileInputStream(file));


        // prints the resulting compilation unit to default system output
//        System.out.println(cu.toString());

        MyVoidVisitorAdapter adapter = new MyVoidVisitorAdapter();
        cu.getResult().get().accept(adapter, null);

        Set<String> packageSet = ParseUtils.packageSet(actionMap);
        if (packageSet.size() > 1) {
            //多包
            for (JSONObject value : actionMap.values()) {
                value.put("actionName", value.getString("packageName") + "/" + value.getString("actionName"));
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(adapter.packagePath.replace("action", "controller")).append(";\n\n");
        for (String s : adapter.importList) {
            sb.append(s);
        }
        sb.append("import com.caimao.sports.controller.BaseController;\n");
        sb.append("import org.springframework.stereotype.Controller;\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        sb.append("import org.springframework.web.bind.annotation.ResponseBody;\n");

        ClassOrInterfaceDeclaration clsData = adapter.classOrInterfaceDeclaration;

//        if(clsData.getComment().isPresent()){
//            sb.append(clsData.getComment().get().toString());
//        }

        sb.append("/**\n" +
                " * Created with IntelliJ IDEA.\n" +
                " * Description: xwork => springMVC\n" +
                " * changed by java parser\n" +
                " * @author genx\n" +
                " * @date 2020/7/8 23:13\n" +
                " */\n");

        sb.append("@Controller\n");
        sb.append("@RequestMapping(\"/");
        if (packageSet.size() == 1) {
            sb.append(packageSet.iterator().next());
        }
        sb.append("\")\n");

        sb.append(clsData.getAccessSpecifier().asString()).append(" class ");

        sb.append(clsData.getNameAsString().replace("Action", "Controller"));

        sb.append(" extends BaseController");
//        if (clsData.getExtendedTypes().size() > 0) {
//            sb.append(" extends ").append(clsData.getExtendedTypes(0).getNameAsString());
//        }
        if (clsData.getImplementedTypes().size() > 0) {
            sb.append(" implements");
            for (int i = 0; i < clsData.getImplementedTypes().size(); i++) {
                sb.append(" ").append(clsData.getImplementedTypes(i).getNameAsString());
                if (i < clsData.getImplementedTypes().size() - 1) {
                    sb.append(",");
                }
            }
        }

        sb.append(" {\n\n");

        String frontSpace = "    ";

        // filed
        for (FieldDeclaration field : clsData.getFields()) {
            if (field.toString().contains("serialVersionUID")) {
                // 忽略 serialVersionUID
                continue;
            }
            sb.append(frontSpace).append(field.toString()).append("\n");
        }

        // method
        for (MethodDeclaration method : clsData.getMethods()) {

            if (method.getComment().isPresent()) {
                sb.append(frontSpace).append(method.getComment().get().toString());
            }

            JSONObject actionItem = actionMap.get(method.getNameAsString());
            if (actionItem != null) {

                if (actionItem.getJSONObject("result").size() == 0) {
                    sb.append("@ResponseBody\n");
                }

                sb.append("@RequestMapping(\"/" + actionItem.getString("actionName") + "\")\n");
            }

            sb.append(method.getDeclarationAsString(true, true, true));


            if (method.getBody().isPresent()) {
                String methodBody = method.getBody().get().toString();

                for (Map.Entry<String, String> entry : actionReturnMap.entrySet()) {
                    methodBody = methodBody.replace(entry.getKey(), entry.getValue());
                }

                methodBody = methodBody.replace("logger.error(e);", "logger.error(\"发生异常\",e);");

                if (actionItem != null) {

                    for (Map.Entry<String, Object> entry : actionItem.getJSONObject("result").entrySet()) {
                        //TODO jsp 处理
                        methodBody = methodBody.replace("\"" + entry.getKey() + "\"", "\"" + entry.getValue() + "\"");
                    }
                }


                sb.append(frontSpace).append(methodBody).append("\n");
            }
        }


        sb.append("\n}");

        File target = new File(sourcePath + classPath.replace("action", "controller").replace("Action", "Controller"));

        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }


        IOUtils.write(sb.toString(), new FileOutputStream(target), Charsets.UTF_8);

    }


    private static class MyVoidVisitorAdapter extends VoidVisitorAdapter<Void> {
        private String packagePath;

        private List<String> importList = new ArrayList(32);

        private ClassOrInterfaceDeclaration classOrInterfaceDeclaration;

        /**
         * 解析 所在包路径
         * @param n
         * @param arg
         */
        @Override
        public void visit(PackageDeclaration n, Void arg) {
            this.packagePath = n.getNameAsString();
            super.visit(n, arg);
        }


        @Override
        public void visit(ImportDeclaration importDeclaration, Void arg) {
            this.importList.add(importDeclaration.toString());
            super.visit(importDeclaration, arg);
        }


        @Override
        public void visit(MethodDeclaration n, Void arg) {

//            System.out.println("method:" + n.getName());

//            for (Parameter parameter : n.getParameters()) {
//                System.out.println(parameter);
//            }


//            BlockStmt blockStmt = n.getBody().get();
//            System.out.println(blockStmt.asBlockStmt());


            super.visit(n, arg);
        }

        /**
         * 解析 类名 继承的类、实现的接口
         * @param n
         * @param arg
         */
        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            this.classOrInterfaceDeclaration = n;
//            System.out.println("class:" + n.getName());
//            System.out.println("fullyQualifiedName:" + n.getFullyQualifiedName());
//            System.out.println("extends:" + n.getExtendedTypes());
//            System.out.println("implements:" + n.getImplementedTypes());

            super.visit(n, arg);
        }


        /**
         * 字段描述
         * @param n
         * @param arg
         */
        @Override
        public void visit(FieldDeclaration n, Void arg) {
//            System.out.println("[FieldDeclaration]" + n);
            super.visit(n, arg);
        }


//        @Override
//        public void visit(VariableDeclarationExpr n, Void arg) {
//            System.out.println("[VariableDeclarationExpr]" + n);
//            super.visit(n, arg);
//        }
//
//        @Override
//        public void visit(MethodCallExpr n, Void arg) {
//            if(n.getScope().isPresent()) {
//                Expression expr = n.getScope().get();
//                System.out.println("#########");
//                System.out.println(expr.toString());
//                System.out.println("MethodCallExpr:" + n.getName());
//                super.visit(n, arg);
//            }
//        }

    }
}
