package com.genx.javadoc.utils;

import com.genx.javadoc.vo.*;
import com.sun.javadoc.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/15 14:43
 */
public class ClassReader {

    private Map<String, ClassDocVO> map = new HashMap<>(2048);

    public ClassReader read(ClassDoc[] classDocs){
        for (ClassDoc classDoc : classDocs) {
            read(classDoc);
        }
        return this;
    }

    public Map<String, ClassDocVO> getResult(){
        return this.map;
    }


    public void read(ClassDoc classDoc){
        if(this.map.containsKey(classDoc.qualifiedName())){
            return;
        }

        ClassDocVO classDocVO = new ClassDocVO();

        classDocVO.setClassName(classDoc.qualifiedName());
        //类 修饰数值
        classDocVO.setModifierSpecifier(classDoc.modifierSpecifier());
        //注释
        classDocVO.setRawCommentText(classDoc.getRawCommentText());

        //读取class上的注解
        classDocVO.setAnnotations(AnnotationUtil.readAnnotationMap(classDoc));

        //类的泛型
        if (classDoc.typeParameters() != null && classDoc.typeParameters().length > 0) {
            List<TypeVariableVO> typeVariableVOList = new ArrayList<>(classDoc.typeParameters().length);
            for (TypeVariable typeVariable : classDoc.typeParameters()) {
                typeVariableVOList.add(readTypeVariable(typeVariable));
            }
            classDocVO.setTypeParameters(typeVariableVOList);
        }

        //类的方法
        classDocVO.setMethods(readMethods(classDoc));

        this.map.put(classDoc.qualifiedName(), classDocVO);

        //读取内部类
        for (ClassDoc innerClassDoc : classDoc.innerClasses(true)) {
            //只读取public的
            read(innerClassDoc);
        }
    }

    private static TypeVariableVO readTypeVariable(TypeVariable typeVariable) {
        TypeVariableVO typeVariableVO = new TypeVariableVO();
        typeVariableVO.setName(typeVariable.typeName());
        typeVariableVO.setDescription(typeVariable.toString());
        return typeVariableVO;
    }


    private static List<MethodDocVO> readMethods(ClassDoc classDoc) {
        //只读取 public 方法
        MethodDoc[] methodDocs = classDoc.methods(true);
        List<MethodDocVO> methods = new ArrayList(methodDocs.length);
        for (MethodDoc methodDoc : methodDocs) {
            methods.add(read(methodDoc));
        }
        return methods;
    }

    private static MethodDocVO read(MethodDoc methodDoc) {
        MethodDocVO methodDocVO = new MethodDocVO();
        methodDocVO.setMethodName(methodDoc.name());
        //方法注释
        methodDocVO.setCommentText(methodDoc.commentText());

        //方法注解
        methodDocVO.setAnnotations(AnnotationUtil.readAnnotationMap(methodDoc));

        //读取参数
        List<TypeDoc> params = new ArrayList<>(methodDoc.parameters().length);
        //读取参数注释
        Map<String, String> paramCommentMap = new HashMap(16);
        for (ParamTag paramTag : methodDoc.paramTags()) {
            paramCommentMap.put(paramTag.parameterName(), paramTag.parameterComment());
        }
        for (Parameter parameter : methodDoc.parameters()) {
            //为每个参数 添加上注释
            params.add(TypeReader.read(parameter.type(), parameter.name(), paramCommentMap.get(parameter.name())));
        }
        methodDocVO.setParams(params);

        //解析返回类型
        methodDocVO.setReturnType(TypeReader.read(methodDoc.returnType(), "", ""));

        //读取 return 注释
        Tag[] returnTags = methodDoc.tags("return");
        if (returnTags.length > 0) {
            methodDocVO.setReturnComment(returnTags[0].text());
        }

        Map<String, String> throwExpections = new HashMap(8);
        for (ThrowsTag throwsTag : methodDoc.throwsTags()) {
            throwExpections.put(throwsTag.exceptionName(), throwsTag.exceptionComment());
        }
        methodDocVO.setThrowExpections(throwExpections);

        return methodDocVO;
    }

}
