package com.delcache.website.common.helper;

import java.util.List;
import java.util.Map;

public class Pager {
    private int totalPage;

    private int page;

    private int pageSize;

    private List list;

    public Pager(List list, int total, Map<String, Object> params) {
        this.list = list;
        this.pageSize = Integer.parseInt(params.getOrDefault("pageSize","10").toString());
        this.page = Integer.parseInt(params.getOrDefault("page","1").toString());
        this.totalPage = (int) Math.ceil(1.0 * total / this.pageSize);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
