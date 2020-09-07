package org.genx.javadoc.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/5 11:10
 */
public class CommentDoc implements Serializable {

    public static final String TYPE_TEXT = "Text";

    /**
     * 文本 Text
     * 其他比如 @linkplain, @link
     */
    private String type;

    private String text;

    private CommentDoc[] inlines;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentDoc[] getInlines() {
        return inlines;
    }

    public void setInlines(CommentDoc[] inlines) {
        this.inlines = inlines;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(256);
        this.appendString(s);
        return s.toString();
    }

    private void appendString(StringBuilder s) {
        if(!TYPE_TEXT.equals(this.type)){
            s.append("{").append(this.type).append(" ");
        }
        if (this.text != null) {
            s.append(this.text);
        } else if (inlines != null) {
            for (CommentDoc inline : inlines) {
                inline.appendString(s);
            }
        }
        if(!TYPE_TEXT.equals(this.type)){
            s.append("}");
        }
    }

    public static CommentDoc of(String comment){
        CommentDoc commentDoc = new CommentDoc();
        commentDoc.setType(TYPE_TEXT);
        commentDoc.setText(comment);
        return commentDoc;
    }

}
