package com.pojo;

import java.io.Serializable;
public class Blog implements Serializable {
    Long id;
    String title;
    String content;
    String description;
    String firstPicture;
    String createTime;
    String updateTime;
    Long appreciation;
    Long views;
    Long collection;
    Long comment;
    Boolean commentable;
    Boolean original;
    Long specialColumnId;
    Long userId;
    String examine;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Long appreciation) {
        this.appreciation = appreciation;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getCollection() {
        return collection;
    }

    public void setCollection(Long collection) {
        this.collection = collection;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }

    public Boolean getOriginal() {
        return original;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public Long getSpecialColumnId() {
        return specialColumnId;
    }

    public void setSpecialColumnId(Long specialColumnId) {
        this.specialColumnId = specialColumnId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public Long getComment() {
        return comment;
    }

    public void setComment(Long comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", appreciation=" + appreciation +
                ", views=" + views +
                ", collection=" + collection +
                ", comment=" + comment +
                ", commentable=" + commentable +
                ", original=" + original +
                ", specialColumnId=" + specialColumnId +
                ", userId=" + userId +
                ", examine='" + examine + '\'' +
                '}';
    }
}
