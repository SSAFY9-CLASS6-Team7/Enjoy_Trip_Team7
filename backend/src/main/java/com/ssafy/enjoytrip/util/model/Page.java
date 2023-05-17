package com.ssafy.enjoytrip.util.model;

import lombok.Data;

@Data
public class Page {
    private int pageNo;
    private int listSize;

    public Page() {
        this(1, 10);
    }

    public Page(int pageNo) {
        this(pageNo, 10);
    }

    public Page(int pageNo, int listSize) {
        this.pageNo = pageNo;
        this.listSize = listSize;
    }

    public int getBeginPage() {
        return (this.pageNo - 1) * this.listSize;
    }
}