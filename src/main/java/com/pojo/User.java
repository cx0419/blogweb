package com.pojo;

import java.io.Serializable;

public class User implements Serializable {
    Long id;
    String account;
    String name;
    String passWord;
    String createTime;
    Boolean gender;
    String introduction;
    String birthday;
    String location;
    Boolean admin;
    String avatar;
    String email;
    Boolean isdelete;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreatTime(String creatTime) {
        this.createTime = creatTime;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", passWord='" + passWord + '\'' +
                ", createTime='" + createTime + '\'' +
                ", gender=" + gender +
                ", introduction='" + introduction + '\'' +
                ", birthday='" + birthday + '\'' +
                ", location='" + location + '\'' +
                ", admin=" + admin +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", isdelete=" + isdelete +
                '}';
    }
}

