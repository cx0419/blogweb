package com.dto;

import java.util.List;

/*
一个pojo代表了一页数据
 */
public class Page<T> {
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 页码
     */
    private int pageNum;

    /**
     * 总页数
     */
    private int pages;
    /**
     * 总条数
     */
    private int total;

    /**
     * 当前页的数据
     */
    private List<T> data;

    /**
     * 构造方法
     * @param pageSize 每一页的条数
     * @param pageNum 本页的页码
     * @param total 一共有多少条数据
     * @param data 这一页的数据
     */
    public Page(int pageSize, int pageNum, int total, List<T> data) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.total = total;
        this.data = data;
        this.pages = total / pageSize + (total % pageSize == 0 ? 0 : 1);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", pages=" + pages +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}
