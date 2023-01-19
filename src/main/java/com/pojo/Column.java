package com.pojo;


public class Column {
    Long id;
    Long userId;
    String name;
    String introduction;
    String picture;
    Boolean show;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "Column{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", picture='" + picture + '\'' +
                ", show=" + show +
                '}';
    }
}
