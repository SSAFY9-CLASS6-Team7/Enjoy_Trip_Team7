package com.ssafy.enjoytrip.util.model;

import lombok.Data;

@Data
public class PageResult {
    private int pageNo;
    private int count;
    private int listSize;
    private int tabSize;
    private int beginPage;
    private int endPage;
    private int lastPage;
    private boolean prev;
    private boolean next;

    public PageResult(int pageNo, int count) {
        this(pageNo, count, 10, 5);
    }

    public PageResult(int pageNo, int count, int listSize, int tabSize) {
        this.pageNo = pageNo;
        this.count = count;
        this.listSize = listSize;
        this.tabSize = tabSize;
        this.lastPage = (int) Math.ceil(count / (double)listSize);

        int currentTab = ((pageNo - 1) / tabSize) + 1;

        this.beginPage = ((currentTab - 1) * tabSize) + 1;
        this.endPage = (currentTab * tabSize);
        endPage = endPage > lastPage ? lastPage : endPage;

        this.prev = beginPage != 1;
        this.next = endPage != lastPage;
    }
}
