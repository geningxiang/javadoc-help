package com.genx.javadoc.vo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 0:04
 */
public class RequestMapping {

    private String name;

    private String[] paths;

    private String[] methods;

    private String[] produces;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }

    public String[] getProduces() {
        return produces;
    }

    public void setProduces(String[] produces) {
        this.produces = produces;
    }
}
