package com.cose.easywu.po;

public class Page {
    private int pageSize = 10; // 每页记录数，默认值为10
    private int pageCode; // 当前页码

    public Page(int pageCode) {
        this.pageCode = pageCode;
    }

    public Page(int pageSize, int pageCode) {
        this.pageSize = pageSize;
        this.pageCode = pageCode;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCode() {
        return pageCode;
    }

    public void setPageCode(int pageCode) {
        this.pageCode = pageCode;
    }
}
