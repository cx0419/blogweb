package com.pojo;

public class UserExtend {
    Long id;
    Long userId;
    int view;
    int original;
    int like;
    int collection;
    int comment;
    int fans;
    String version;

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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "UserExtend{" +
                "id=" + id +
                ", userId=" + userId +
                ", view=" + view +
                ", original=" + original +
                ", like=" + like +
                ", collection=" + collection +
                ", comment=" + comment +
                ", fans=" + fans +
                ", version='" + version + '\'' +
                '}';
    }
}
