//package org.genx.javadoc.utils;
//
//import org.genx.javadoc.bean.AnnotationDesc;
//import org.genx.javadoc.bean.ClassDoc;
//import org.genx.javadoc.bean.CommentDoc;
//import org.genx.javadoc.bean.TypeVariableDoc;
//import org.genx.javadoc.proto.JavaDocProto;
//
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * @author genx
// * @date 2020/9/5 19:20
// */
//public class ProtoConverter {
//
//    public JavaDocProto.ClassDoc toProto(ClassDoc classDoc){
//        JavaDocProto.ClassDoc.Builder builder = JavaDocProto.ClassDoc.newBuilder();
//        builder.setClassName(classDoc.getClassName());
//        builder.setClassInfo(classDoc.getClassInfo());
//        builder.setModifierSpecifier(classDoc.getModifierSpecifier());
//
//        if(classDoc.getAnnotations() != null){
//            for (Map.Entry<String, AnnotationDesc> entry : classDoc.getAnnotations().entrySet()) {
//                builder.putAnnotations(entry.getKey(), toProto(entry.getValue()));
//            }
//        }
//        builder.setComment(toProto(classDoc.getComment()));
//
//        if(classDoc.getTags() != null){
//            for (Map.Entry<String, CommentDoc> entry : classDoc.getTags().entrySet()) {
//                builder.putTags(entry.getKey(), toProto(entry.getValue()));
//            }
//        }
//
//        if( classDoc.getTypeParameters() != null){
//            for (int i = 0; i <  classDoc.getTypeParameters().size(); i++) {
//                builder.setTypeParameters(i, toProto(classDoc.getTypeParameters().get(i)));
//            }
//        }
//
//        return builder.build();
//
//    }
//
//    private JavaDocProto.AnnotationDesc toProto(AnnotationDesc annotationDesc){
//        JavaDocProto.AnnotationDesc.Builder builder = JavaDocProto.AnnotationDesc.newBuilder();
//        builder.setName(annotationDesc.getName());
//        builder.setQualifiedName(annotationDesc.getQualifiedName());
//        if(annotationDesc.getData() != null){
//            for (Map.Entry<String, String[]> entry : annotationDesc.getData().entrySet()) {
//                JavaDocProto.AnnotationDesc.AnnotationData.Builder dataBuilder = JavaDocProto.AnnotationDesc.AnnotationData.newBuilder();
//                if(entry.getValue() != null){
//                    for (int i = 0; i < entry.getValue().length; i++) {
//                        dataBuilder.setValues(i, entry.getValue()[i]);
//                    }
//                }
//                builder.putData(entry.getKey(), dataBuilder.build());
//            }
//        }
//        return builder.build();
//    }
//
//    private JavaDocProto.CommentDoc toProto(CommentDoc commentDoc){
//        JavaDocProto.CommentDoc.Builder builder = JavaDocProto.CommentDoc.newBuilder();
//        builder.setType(commentDoc.getType());
//        builder.setText(commentDoc.getText());
//
//        if(commentDoc.getInlines() != null && commentDoc.getInlines().length > 0){
//            for (int i = 0; i < commentDoc.getInlines().length; i++) {
//                builder.setInlines(i, toProto(commentDoc.getInlines()[i]));
//            }
//        }
//        return builder.build();
//    }
//
//    private JavaDocProto.TypeVariableDoc toProto(TypeVariableDoc typeVariableDoc){
//        JavaDocProto.TypeVariableDoc.Builder builder = JavaDocProto.TypeVariableDoc.newBuilder();
//        builder.setName(typeVariableDoc.getName());
//        builder.setDescription(typeVariableDoc.getDescription());
//        return builder.build();
//    }
//
//}
