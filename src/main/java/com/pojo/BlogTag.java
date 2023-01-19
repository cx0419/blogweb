package com.pojo;

public class BlogTag {
    Long id;
    Long blogId;
    Long TagId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getTagId() {
        return TagId;
    }

    public void setTagId(Long tagId) {
        TagId = tagId;
    }

    @Override
    public String toString() {
        return "BlogTag{" +
                "id=" + id +
                ", blogId=" + blogId +
                ", tagId=" + TagId +
                '}';
    }
}
