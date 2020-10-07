package com.cash.online.CashOnline.model.dto;

public class PagingDTO {

    private final int size;
    private final int page;
    private final long total;

    public PagingDTO(int size, int page, long total){
        this.size = size;
        this.page = page;
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }
}
