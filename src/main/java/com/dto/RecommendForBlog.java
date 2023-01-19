package com.dto;

import com.pojo.Blog;

public class RecommendForBlog {
    Blog blog;
    Boolean isRecommend;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    @Override
    public String toString() {
        return "RecommendForBlog{" +
                "blog=" + blog +
                ", isRecommend=" + isRecommend +
                '}';
    }
}
