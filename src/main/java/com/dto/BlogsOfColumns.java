package com.dto;

import com.pojo.Blog;
import com.pojo.BlogColumn;
import com.pojo.Column;

import java.util.List;

/**
 * 该dto完成B端显示一个专栏所有博客
 */
public class BlogsOfColumns {
    private Column column;
    //该专栏含有的columns 的 id
    private List<BlogColumn> blogColumns;
    private List<Blog> blogs;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public List<BlogColumn> getBlogColumns() {
        return blogColumns;
    }

    public void setBlogColumns(List<BlogColumn> blogColumns) {
        this.blogColumns = blogColumns;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "BlogsOfColumns{" +
                "column=" + column +
                ", blogColumns=" + blogColumns +
                ", blogs=" + blogs +
                '}';
    }
}
