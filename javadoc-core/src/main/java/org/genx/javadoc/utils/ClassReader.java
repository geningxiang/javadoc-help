package org.genx.javadoc.utils;

import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;
import org.genx.javadoc.bean.ClassDoc;
import org.genx.javadoc.bean.MethodDoc;
import org.genx.javadoc.bean.TypeDoc;
import org.genx.javadoc.bean.TypeVariableDoc;
import org.genx.javadoc.proto.JavaDocProto;

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

    private final JavaDocBuilder.JavaDocEnv env;

    public ClassReader(JavaDocBuilder.JavaDocEnv env) {
        this.env = env;
    }


    public void read() {
        for (com.sun.javadoc.ClassDoc classDoc : env.getClassDocMap().values()) {
            read(classDoc);
        }
    }


    public void read(com.sun.javadoc.ClassDoc classDoc) {
        if (env.exist(classDoc.qualifiedTypeName())) {
            //已解析
            return;
        }
        try {

            ClassDoc classDocVO = new ClassDoc();
            classDocVO.setClassName(classDoc.qualifiedTypeName());
            env.add(classDocVO);

            //类 修饰数值
            classDocVO.setModifierSpecifier(classDoc.modifierSpecifier());

            //注释
            classDocVO.setComment(CommentUtil.read(classDoc.inlineTags()));
            //注释中的 tag   @author @date  等
            classDocVO.setTags(CommentUtil.readTagWithMap(classDoc.tags()));

            //读取class上的注解
            classDocVO.setAnnotations(AnnotationUtil.readAnnotationMap(classDoc));

            //判断类是否 实现循环接口
//            classDocVO.setIterable(CoreUtil.isIterable(classDoc));

//            classDocVO.setType(CoreUtil.assertType(classDoc).name());

            //类的泛型
            if (classDoc.typeParameters() != null && classDoc.typeParameters().length > 0) {
                List<TypeVariableDoc> typeVariableVOList = new ArrayList(classDoc.typeParameters().length);
                for (com.sun.javadoc.TypeVariable typeVariable : classDoc.typeParameters()) {
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
        for (com.sun.javadoc.ClassDoc innerClassDoc : classDoc.innerClasses(true)) {
            //只读取public的
            read(innerClassDoc);
        }
    }

    private TypeVariableDoc readTypeVariable(com.sun.javadoc.TypeVariable typeVariable) {
        TypeVariableDoc typeVariableVO = new TypeVariableDoc();
        typeVariableVO.setName(typeVariable.typeName());
        typeVariableVO.setDescription(typeVariable.toString());
        return typeVariableVO;
    }

    /**
     * 读取类的变量
     * @param classDoc
     * @return
     */
    private List<TypeDoc> readFields(com.sun.javadoc.ClassDoc classDoc) {
        com.sun.javadoc.FieldDoc[] fieldDocs = classDoc.fields(false);
        List<TypeDoc> fields = new ArrayList(fieldDocs.length);
        for (com.sun.javadoc.FieldDoc fieldDoc : fieldDocs) {
            fields.add(readType(fieldDoc));
        }
        return fields;
    }


    private List<MethodDoc> readMethods(com.sun.javadoc.ClassDoc classDoc) {
        com.sun.javadoc.MethodDoc[] methodDocs = classDoc.methods(false);
        List<MethodDoc> methods = new ArrayList(methodDocs.length);
        for (com.sun.javadoc.MethodDoc methodDoc : methodDocs) {
            methods.add(readMethod(methodDoc));
        }
        return methods;
    }

    private MethodDoc readMethod(com.sun.javadoc.MethodDoc methodDoc) {
        MethodDoc methodDocVO = new MethodDoc();
        methodDocVO.setMethodName(methodDoc.name());
        methodDocVO.setModifierSpecifier(methodDoc.modifierSpecifier());
        methodDocVO.setComment(CommentUtil.read(methodDoc.inlineTags()));
        methodDocVO.setAnnotations(AnnotationUtil.readAnnotationMap(methodDoc));
        methodDocVO.setTags(CommentUtil.readTagWithMap(methodDoc.tags()));

        //读取参数
        List<TypeDoc> params = new ArrayList<>(methodDoc.parameters().length);
        //读取参数注释
        Map<String, Tag[]> paramCommentMap = new HashMap(16);
        for (com.sun.javadoc.ParamTag paramTag : methodDoc.paramTags()) {
            paramCommentMap.put(paramTag.parameterName(), paramTag.inlineTags());
        }
        for (Parameter parameter : methodDoc.parameters()) {

            params.add(readType(parameter.type(), parameter.name(), paramCommentMap.get(parameter.name()), parameter.annotations(), null, 0));
        }
        methodDocVO.setParams(params);

        //解析返回类型
        methodDocVO.setReturnType(readType(methodDoc.returnType(), "", methodDoc.tags("return"), null, null, 0));

        //方法的显式抛出异常
        methodDocVO.setThrowExpections(CoreUtil.readThrowExpections(methodDoc));

        return methodDocVO;
    }

    public TypeDoc readType(com.sun.javadoc.FieldDoc fieldDoc) {
        return readType(fieldDoc.type(), fieldDoc.name(), fieldDoc.inlineTags(), fieldDoc.annotations(), fieldDoc.tags(), fieldDoc.modifierSpecifier());
    }

    public TypeDoc readType(com.sun.javadoc.Type type, String name, Tag[] inlineTags, com.sun.javadoc.AnnotationDesc[] annotations, Tag[] tags, int modifierSpecifier) {
        onTypeReaded(type);

        TypeDoc typeDoc = new TypeDoc();
        typeDoc.setClassInfo(type.toString());
        typeDoc.setClassName(type.qualifiedTypeName());
        typeDoc.setName(name);
        typeDoc.setComment(CommentUtil.read(inlineTags));
        typeDoc.setDimension(type.dimension().length() / 2 + 1);
        typeDoc.setAnnotations(AnnotationUtil.readAnnotationMap(annotations));
        typeDoc.setTags(CommentUtil.readTagWithMap(tags));
        typeDoc.setParameters(CoreUtil.readParameteres(type));
        typeDoc.setModifierSpecifier(modifierSpecifier);
        return typeDoc;
    }

    private void onTypeReaded(com.sun.javadoc.Type type) {
        if (type != null && type.asClassDoc() != null) {
            read(type.asClassDoc());
        }
    }

}
