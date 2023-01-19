package com.pojo;

public class Like {
    Long id;
    Long blogId;
    Long userId;
    Boolean isdelete;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", blogId=" + blogId +
                ", userId=" + userId +
                ", isdelete=" + isdelete +
                '}';
    }
}
