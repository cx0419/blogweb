package com.dto;

import com.pojo.Comment;
import com.pojo.User;

public class SonComment {
    Comment comment;
    User user;
    User parentUser;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User parentUser) {
        this.parentUser = parentUser;
    }

    @Override
    public String toString() {
        return "SonComment{" +
                "comment=" + comment +
                ", user=" + user +
                ", parentUser=" + parentUser +
                '}';
    }
}
