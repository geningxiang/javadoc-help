package org.genx.javadoc.vo;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/7 17:54
 */
public class TypeParameterizedDoc {

    private String text;

    private String className;

    /**
     * 维度
     * 例如 方法返回 String[]
     */
    private int dimension = 1;


    private TypeParameterizedDoc[] parameteres;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public TypeParameterizedDoc[] getParameteres() {
        return parameteres;
    }

    public void setParameteres(TypeParameterizedDoc[] parameteres) {
        this.parameteres = parameteres;
    }
}
