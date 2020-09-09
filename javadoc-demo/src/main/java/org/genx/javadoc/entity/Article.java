package org.genx.javadoc.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/8 21:17
 */
public class Article {

    /**
     * 资讯ID
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 资讯内容
     */
    private String content;

    /**
     * 资讯创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
