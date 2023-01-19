package com.pojo;


public class Attention {
    private Long id;
    private Long bloggerId;
    private Long fansId;
    private String createTime;
    private Boolean delete;

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBloggerId() {
        return bloggerId;
    }

    public void setBloggerId(Long bloggerId) {
        this.bloggerId = bloggerId;
    }

    public Long getFansId() {
        return fansId;
    }

    public void setFansId(Long fansId) {
        this.fansId = fansId;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Attention{" +
                "id=" + id +
                ", bloggerId=" + bloggerId +
                ", fansId=" + fansId +
                ", delete=" + delete +
                ", createTime=" + createTime +
                '}';
    }
}
