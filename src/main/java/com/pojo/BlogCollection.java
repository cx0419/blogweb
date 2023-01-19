package com.pojo;

/**
 * 该类为博客与收藏夹的中间类
 */
public class BlogCollection {
    Long id;
    Long collectionId;
    Long blogId;
    String updateTime;
    Boolean isdelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    @Override
    public String toString() {
        return "BlogCollection{" +
                "id=" + id +
                ", collectionId=" + collectionId +
                ", blogId=" + blogId +
                ", updateTime='" + updateTime + '\'' +
                ", isdelete=" + isdelete +
                '}';
    }
}
