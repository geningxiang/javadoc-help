package org.genx.javadoc.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;

public class Test {

    public static void main(String[] args) throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/");

        String path = resource.getFile().getAbsolutePath();


        File file = new File(path + "/../../src/main/java/com/genx/javadoc/controller/AppController.java");

        // parse the file
        ParseResult<CompilationUnit> cu = new JavaParser().parse(new FileInputStream(file));


        // prints the resulting compilation unit to default system output
//        System.out.println(cu.toString());
        cu.getResult().get().accept(new MethodVisitor(), null);
//        cu.accept(l);
    }


    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
//        @Override
//        public void visit(MethodDeclaration n, Void arg) {
//            /* here you can access the attributes of the method.
//             this method will be called for all methods in this
//             CompilationUnit, including inner class methods */
//            System.out.println("method:" + n.getName());
//
////            BlockStmt blockStmt = n.getBody().get();
////
////
////            System.out.println(blockStmt.asBlockStmt());
////
//
//            super.visit(n, arg);
//        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            System.out.println("class:" + n.getName());
            System.out.println("extends:" + n.getExtendedTypes());
            System.out.println("implements:" + n.getImplementedTypes());

            super.visit(n, arg);
        }

        @Override
        public void visit(PackageDeclaration n, Void arg) {
            System.out.println("package:" + n.getName());
            super.visit(n, arg);
        }

        @Override
        public void visit(FieldDeclaration n, Void arg) {
            System.out.println("[FieldDeclaration]"+n);
            super.visit(n, arg);
        }

        /**
         * 变量定义
         * @param n
         * @param arg
         */
        @Override
        public void visit(VariableDeclarationExpr n, Void arg){
            System.out.println("[VariableDeclarationExpr]"+n);
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {

            Expression expr = n.getScope().get();



                System.out.println("#########");


                System.out.println(expr.toString());

                System.out.println("MethodCallExpr:" + n.getName());


            super.visit(n, arg);
        }

    }
}
