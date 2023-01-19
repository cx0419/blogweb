package com.pojo;

public class BeLikeScore {
    Long userid;
    double likeDegree;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public double getLikeDegree() {
        return likeDegree;
    }

    public void setLikeDegree(double likeDegree) {
        this.likeDegree = likeDegree;
    }

    @Override
    public String toString() {
        return "BeLikeScore{" +
                "userid=" + userid +
                ", likeDegree=" + likeDegree +
                '}';
    }


}
