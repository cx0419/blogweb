package com.dto;

import com.pojo.Column;

public class ColumnAndNum {
    Column column;
    int num;

    public ColumnAndNum() {
        column = new Column();
        this.num = 0;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ColumnAndNum{" +
                "column=" + column +
                ", num=" + num +
                '}';
    }
}
