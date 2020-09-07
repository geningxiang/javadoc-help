package org.genx.javadoc.plugin.lombok;

import org.genx.javadoc.bean.ClassDoc;
import org.genx.javadoc.bean.JavaDoc;
import org.genx.javadoc.bean.MethodDoc;
import org.genx.javadoc.bean.TypeDoc;
import org.genx.javadoc.plugin.IJavaDocPlugin;
import org.genx.javadoc.utils.CoreUtil;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * LomBok 插件 追加上 get、set 方法
 * 暂时不考虑 toString、hash、equal
 * @author genx
 * @date 2020/3/6 22:21
 */
public class LomBokPlugin implements IJavaDocPlugin {

    private final int PUBLIC_MODIFIER = 1;

    private final String LOMBOK_DATA = "lombok.Data";

    private final String LOMBOK_GETTER = "lombok.Getter";

    private final String LOMBOK_SETTER = "lombok.Setter";

    @Override
    public void handle(JavaDoc javaDocVO) {
        for (ClassDoc classDoc : javaDocVO.getClassDocs().values()) {
            handle(classDoc);
        }
    }

    public void handle(ClassDoc classDoc) {
        boolean addGet = classDoc.hasAnnotation(LOMBOK_DATA) || classDoc.hasAnnotation(LOMBOK_GETTER);
        boolean addSet = classDoc.hasAnnotation(LOMBOK_DATA) || classDoc.hasAnnotation(LOMBOK_SETTER);

        for (TypeDoc field : classDoc.getFields().values()) {
            if (!Modifier.isTransient(field.getModifierSpecifier())
                    && !Modifier.isStatic(field.getModifierSpecifier())) {
                //非transient && 非static

                if (addGet || field.hasAnnotation(LOMBOK_GETTER)) {
                    this.addGetMethod(classDoc, field);
                }

                if (!Modifier.isFinal(field.getModifierSpecifier())) {
                    //非 final

                    if (addSet || field.hasAnnotation(LOMBOK_SETTER)) {
                        this.addSetMethod(classDoc, field);
                    }
                }

            }
        }

    }

    private void addGetMethod(ClassDoc classDoc, TypeDoc field) {
        MethodDoc methodDoc = new MethodDoc();
        methodDoc.setMethodName("get" + CoreUtil.upperCase(field.getName()));
        methodDoc.setModifierSpecifier(PUBLIC_MODIFIER);
        methodDoc.setParams(null);
        methodDoc.setReturnType(field.copy());
        classDoc.putMethod(methodDoc.toString(), methodDoc);
    }

    private void addSetMethod(ClassDoc classDoc, TypeDoc field) {
        //判断 不是 final 字段
        if (!Modifier.isFinal(field.getModifierSpecifier())) {
            MethodDoc methodDoc = new MethodDoc();
            methodDoc.setMethodName("set" + CoreUtil.upperCase(field.getName()));
            methodDoc.setModifierSpecifier(PUBLIC_MODIFIER);
            List<TypeDoc> params = new ArrayList(1);
            params.add(field.copy());
            methodDoc.setParams(params);
            methodDoc.setReturnType(TypeDoc.ofVoid());
            classDoc.putMethod(methodDoc.toString(), methodDoc);
        }
    }
}
