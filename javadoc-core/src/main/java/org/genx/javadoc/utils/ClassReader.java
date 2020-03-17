package org.genx.javadoc.utils;

import com.sun.javadoc.*;
import org.genx.javadoc.vo.ClassDocVO;
import org.genx.javadoc.vo.MethodDocVO;
import org.genx.javadoc.vo.TypeDoc;
import org.genx.javadoc.vo.TypeVariableVO;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2020/2/15 14:43
 */
public class ClassReader {

    private final JavaDocBuilder.JavaDocEnv env;

    public ClassReader(JavaDocBuilder.JavaDocEnv env) {
        this.env = env;
    }


    public void read(Collection<ClassDoc> classDocs) {
        for (ClassDoc classDoc : classDocs) {
            read(classDoc);
        }
    }


    public void read(ClassDoc classDoc) {
        if (env.exist(classDoc.qualifiedTypeName())) {
            return;
        }

        try {

            ClassDocVO classDocVO = new ClassDocVO();
            classDocVO.setClassName(classDoc.qualifiedTypeName());
            env.add(classDocVO);

            //类 修饰数值
            classDocVO.setModifierSpecifier(classDoc.modifierSpecifier());

            //注释
            classDocVO.setComment(classDoc.commentText());

            //读取class上的注解
            classDocVO.setAnnotations(AnnotationUtil.readAnnotationMap(classDoc));

            classDocVO.setTags(CoreUtil.readTagMap(classDoc));

            //判断类是否 实现循环接口
            classDocVO.setIterable(CoreUtil.isIterable(classDoc));

            classDocVO.setType(CoreUtil.assertType(classDoc).name());

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

        } catch (Exception e) {
            e.printStackTrace();
        }
        //读取内部类
        for (ClassDoc innerClassDoc : classDoc.innerClasses(true)) {
            //只读取public的
            read(innerClassDoc);
        }
    }

    private TypeVariableVO readTypeVariable(TypeVariable typeVariable) {
        TypeVariableVO typeVariableVO = new TypeVariableVO();
        typeVariableVO.setName(typeVariable.typeName());
        typeVariableVO.setDescription(typeVariable.toString());
        return typeVariableVO;
    }

    private List<TypeDoc> readFields(ClassDoc classDoc) {
        FieldDoc[] fieldDocs = classDoc.fields(false);
        List<TypeDoc> fields = new ArrayList(fieldDocs.length);
        for (FieldDoc fieldDoc : fieldDocs) {
            fields.add(readType(fieldDoc));
        }
        return fields;
    }


    private List<MethodDocVO> readMethods(ClassDoc classDoc) {
        MethodDoc[] methodDocs = classDoc.methods(false);
        List<MethodDocVO> methods = new ArrayList(methodDocs.length);
        for (MethodDoc methodDoc : methodDocs) {
            methods.add(readMethod(methodDoc));
        }
        return methods;
    }

    private MethodDocVO readMethod(MethodDoc methodDoc) {
        MethodDocVO methodDocVO = new MethodDocVO();
        methodDocVO.setMethodName(methodDoc.name());
        methodDocVO.setClassInfo(null);
        methodDocVO.setClassName(null);
        methodDocVO.setModifierSpecifier(methodDoc.modifierSpecifier());
        methodDocVO.setComment(methodDoc.commentText());
        methodDocVO.setAnnotations(AnnotationUtil.readAnnotationMap(methodDoc));
        methodDocVO.setTags(CoreUtil.readTagMap(methodDoc));

        //读取参数
        List<TypeDoc> params = new ArrayList<>(methodDoc.parameters().length);
        //读取参数注释
        Map<String, String> paramCommentMap = new HashMap(16);
        for (ParamTag paramTag : methodDoc.paramTags()) {
            paramCommentMap.put(paramTag.parameterName(), paramTag.parameterComment());
        }
        for (Parameter parameter : methodDoc.parameters()) {
            //为每个参数 添加上注释
            params.add(readType(parameter, paramCommentMap.get(parameter.name())));
        }
        methodDocVO.setParams(params);

        //读取 return 注释
        String returnComment = null;
        Tag[] returnTags = methodDoc.tags("return");
        if (returnTags.length > 0) {
            returnComment = returnTags[0].text();
        }

        //解析返回类型
        methodDocVO.setReturnType(readType(methodDoc.returnType(), "", returnComment, null, null, 0));

        //方法的显式抛出异常
        methodDocVO.setThrowExpections(CoreUtil.readThrowExpections(methodDoc));

        return methodDocVO;
    }


    public TypeDoc readType(Parameter parameter, String comment) {
        return readType(parameter.type(), parameter.name(), comment, parameter.annotations(), null, 0);
    }

    public TypeDoc readType(FieldDoc fieldDoc) {
        return readType(fieldDoc.type(), fieldDoc.name(), fieldDoc.commentText(), fieldDoc.annotations(), CoreUtil.readTagMap(fieldDoc), fieldDoc.modifierSpecifier());
    }

    public TypeDoc readType(Type type, String name, String comment, AnnotationDesc[] annotations, Map<String, String> tags, int modifierSpecifier) {
        onTypeReaded(type);

        TypeDoc typeDoc = new TypeDoc();
        typeDoc.setClassInfo(type.toString());
        typeDoc.setClassName(type.qualifiedTypeName());
        typeDoc.setName(name);
        typeDoc.setComment(comment);
        typeDoc.setDimension(type.dimension().length() / 2 + 1);
        typeDoc.setAnnotations(AnnotationUtil.readAnnotationMap(annotations));
        typeDoc.setTags(tags);
        typeDoc.setParameteres(CoreUtil.readParameteres(type));
        typeDoc.setModifierSpecifier(modifierSpecifier);
        return typeDoc;
    }

    private void onTypeReaded(Type type) {
        if (type != null && type.asClassDoc() != null) {
            read(type.asClassDoc());
        }
    }

}
