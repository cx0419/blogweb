package com.dto;

import com.pojo.Comment;
import com.pojo.User;

import java.util.List;

public class TopComment {
    User user;
    Comment comment;
    List<SonComment> sonComments;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<SonComment> getSonComments() {
        return sonComments;
    }

    public void setSonComments(List<SonComment> sonComments) {
        this.sonComments = sonComments;
    }

    @Override
    public String toString() {
        return "TopComment{" +
                "user=" + user +
                ", comment=" + comment +
                ", sonComments=" + sonComments +
                '}';
    }
}
