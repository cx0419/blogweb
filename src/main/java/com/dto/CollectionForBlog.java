package com.dto;

import com.pojo.Collection;

public class CollectionForBlog {
    Collection collection;
    //true 代表了这个收藏夹包含了此文章 false代表了不包含
    Boolean status;

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CollectionForBlog{" +
                "collection=" + collection +
                ", status=" + status +
                '}';
    }
}
