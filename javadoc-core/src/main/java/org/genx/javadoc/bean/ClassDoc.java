package org.genx.javadoc.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/4 21:31
 */
public class ClassDoc extends AbsDoc {

    /**
     * 类的泛型
     * 例如 Map<K,V>
     */
    private List<TypeVariableDoc> typeParameters;

    /**
     * 实现的接口
     */
    private Set<String> interfaceTypes;

    /**
     * 变量
     */
    private Map<String, TypeDoc> fields = new TreeMap();

    /**
     * 方法
     */
    private Map<String, MethodDoc> methods = new TreeMap();

    public List<TypeVariableDoc> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<TypeVariableDoc> typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Map<String, TypeDoc> getFields() {
        return fields;
    }

    public void setFields(Map<String, TypeDoc> fields) {
        if(fields != null) {
            this.fields.putAll(fields);
        }
    }

    public void putField(String name, TypeDoc field) {
        this.fields.put(name, field);
    }

    public Map<String, MethodDoc> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, MethodDoc> methods) {
        if(methods != null) {
            this.methods.putAll(methods);
        }
    }

    public void putMethod(String name, MethodDoc method) {
        this.methods.put(name, method);
    }

    public Set<String> getInterfaceTypes() {
        return interfaceTypes;
    }

    public void setInterfaceTypes(Set<String> interfaceTypes) {
        this.interfaceTypes = interfaceTypes;
    }
}
