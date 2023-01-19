package com.pojo;

/**
 * 用户对于一个博客的喜好程度
 * 喜好程度分 喜欢+0.3 收藏+0.5
 */
public class LikeScore {
    Long blogId;
    double likeDegree;


    public LikeScore(){
        likeDegree = 0;
    }
    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public double getLikeDegree() {
        return likeDegree;
    }

    public void setLikeDegree(double likeDegree) {
        this.likeDegree = likeDegree;
    }

    @Override
    public String toString() {
        return "LikeScore{" +
                "blogId=" + blogId +
                ", likeDegree=" + likeDegree +
                '}';
    }
}
