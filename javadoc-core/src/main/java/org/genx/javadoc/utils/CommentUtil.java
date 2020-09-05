package org.genx.javadoc.utils;

import com.sun.javadoc.Tag;
import org.genx.javadoc.bean.CommentDoc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/5 13:48
 */
public class CommentUtil {

    public static CommentDoc read(Tag[] tags) {
        if (tags == null || tags.length == 0) {
            return null;
        }
        if (tags.length == 1) {
            return read(tags[0]);
        }
        CommentDoc commentDoc = new CommentDoc();
        commentDoc.setType(CommentDoc.TYPE_TEXT);
        CommentDoc[] inlines = new CommentDoc[tags.length];
        for (int i = 0; i < tags.length; i++) {
            inlines[i] = read(tags[i]);
        }
        commentDoc.setInlines(inlines);
        return commentDoc;
    }

    public static Map<String, CommentDoc> readTagWithMap(Tag[] tags) {
        if (tags != null && tags.length > 0) {
            Map<String, CommentDoc> tagMap = new HashMap(16);
            for (Tag tag : tags) {
                tagMap.put(tag.name(), read(tag.inlineTags()));
            }
            return tagMap;
        }
        return null;
    }


    public static CommentDoc read(Tag tag) {
        CommentDoc commentDoc = new CommentDoc();
        commentDoc.setType(tag.name());
        if (CommentDoc.TYPE_TEXT.equals(tag.name())) {
            commentDoc.setText(tag.text());
        } else if (tag.inlineTags() != null && tag.inlineTags().length > 1) {
            CommentDoc[] inlines = new CommentDoc[tag.inlineTags().length];
            for (int i = 0; i < tag.inlineTags().length; i++) {
                inlines[i] = read(tag.inlineTags()[i]);
            }
            commentDoc.setInlines(inlines);
        } else {
            commentDoc.setText(tag.text());
        }
        return commentDoc;
    }
}
