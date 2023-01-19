package com.pojo;

import java.util.List;

public class BlogVector {
    Long blogid;
    List<BeLikeScore> beLikeScores;

    public Long getBlogid() {
        return blogid;
    }

    public void setBlogid(Long blogid) {
        this.blogid = blogid;
    }

    public List<BeLikeScore> getBeLikeScores() {
        return beLikeScores;
    }

    public void setBeLikeScores(List<BeLikeScore> beLikeScores) {
        this.beLikeScores = beLikeScores;
    }

    @Override
    public String toString() {
        return "BlogVector{" +
                "blogid=" + blogid +
                ", beLikeScores=" + beLikeScores +
                '}';
    }
}
