package com.dto;

import com.pojo.User;


public class UserForFocus {
    User user;
    Boolean status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FocusEachOther{" +
                "user=" + user +
                ", status=" + status +
                '}';
    }
}
