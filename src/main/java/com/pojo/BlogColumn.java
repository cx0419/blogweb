package com.pojo;

public class BlogColumn {
    Long id;
    Long columnId;
    Long blogId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "BlogColumn{" +
                "id=" + id +
                ", columnId=" + columnId +
                ", blogId=" + blogId +
                '}';
    }
}
