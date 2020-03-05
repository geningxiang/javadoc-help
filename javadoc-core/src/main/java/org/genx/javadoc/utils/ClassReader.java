package org.genx.javadoc.utils;

import com.sun.javadoc.*;
import org.genx.javadoc.vo.*;
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

    public ClassReader read(ClassDoc[] classDocs) {
        for (ClassDoc classDoc : classDocs) {
            read(classDoc);
        }
        return this;
    }

    public Map<String, ClassDocVO> getResult() {
        return this.map;
    }


    public void read(ClassDoc classDoc) {
        if (this.map.containsKey(classDoc.qualifiedName())) {
            return;
        }

        ClassDocVO classDocVO = new ClassDocVO();

        classDocVO.setClassName(classDoc.qualifiedName());
        //类 修饰数值
        classDocVO.setModifierSpecifier(classDoc.modifierSpecifier());

        //注释
        classDocVO.setComment(classDoc.commentText());

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

        classDocVO.setFields(readFields(classDoc));

        //类的方法
        classDocVO.setMethods(readMethods(classDoc));

        classDocVO.setTags(CoreUtil.readTagMap(classDoc));


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

    private static List<FieldDocVO> readFields(ClassDoc classDoc) {
        FieldDoc[] fieldDocs = classDoc.fields(false);
        List<FieldDocVO> fields = new ArrayList(fieldDocs.length);
        for (FieldDoc methodDoc : fieldDocs) {
            fields.add(read(methodDoc));
        }
        return fields;
    }

    private static FieldDocVO read(FieldDoc fieldDoc) {
        FieldDocVO fieldDocVO = new FieldDocVO();
        fieldDocVO.setFieldName(fieldDoc.name());
        fieldDocVO.setModifierSpecifier(fieldDoc.modifierSpecifier());
        fieldDocVO.setClassInfo(fieldDoc.type().toString());
        fieldDocVO.setClassName(fieldDoc.type().qualifiedTypeName());
        baseRead(fieldDoc, fieldDocVO);
        return fieldDocVO;
    }

    private static List<MethodDocVO> readMethods(ClassDoc classDoc) {
        MethodDoc[] methodDocs = classDoc.methods(false);
        List<MethodDocVO> methods = new ArrayList(methodDocs.length);
        for (MethodDoc methodDoc : methodDocs) {
            methods.add(read(methodDoc));
        }
        return methods;
    }

    private static MethodDocVO read(MethodDoc methodDoc) {
        MethodDocVO methodDocVO = new MethodDocVO();
        methodDocVO.setMethodName(methodDoc.name());
        methodDocVO.setClassInfo(methodDoc.returnType().toString());
        methodDocVO.setClassName(methodDoc.returnType().qualifiedTypeName());
        baseRead(methodDoc, methodDocVO);

        //读取参数
        List<TypeDoc> params = new ArrayList<>(methodDoc.parameters().length);
        //读取参数注释
        Map<String, String> paramCommentMap = new HashMap(16);
        for (ParamTag paramTag : methodDoc.paramTags()) {
            paramCommentMap.put(paramTag.parameterName(), paramTag.parameterComment());
        }
        for (Parameter parameter : methodDoc.parameters()) {
            //为每个参数 添加上注释
            params.add(TypeReader.read(parameter, paramCommentMap.get(parameter.name())));
        }
        methodDocVO.setParams(params);

        //解析返回类型
        methodDocVO.setReturnType(TypeReader.read(methodDoc.returnType(), "", "", null));

        Map<String, String> throwExpections = new HashMap(8);
        for (ThrowsTag throwsTag : methodDoc.throwsTags()) {
            throwExpections.put(throwsTag.exceptionName(), throwsTag.exceptionComment());
        }
        methodDocVO.setThrowExpections(throwExpections);

        return methodDocVO;
    }

    private static void baseRead(ProgramElementDoc doc, AbsDocVO docVo){
        docVo.setComment(doc.commentText());
        docVo.setAnnotations(AnnotationUtil.readAnnotationMap(doc));
        docVo.setTags(CoreUtil.readTagMap(doc));
    }

}
