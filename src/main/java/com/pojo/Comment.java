package com.pojo;

public class Comment {
    Long id;
    Long userId;
    Long blogId;
    String createTime;
    Long parentCommentId;
    Long topCommentId;
    Boolean isdelete;
    String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Long getTopCommentId() {
        return topCommentId;
    }

    public void setTopCommentId(Long topCommentId) {
        this.topCommentId = topCommentId;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", blogId=" + blogId +
                ", createTime='" + createTime + '\'' +
                ", parentCommentId=" + parentCommentId +
                ", topCommentId=" + topCommentId +
                ", isdelete=" + isdelete +
                ", content='" + content + '\'' +
                '}';
    }
}
