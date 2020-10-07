package com.cash.online.CashOnline.model.dto;

import java.util.List;

public class LoanPageDTO {

    private final List<LoanDTO> items;
    private final PagingDTO paging;

    public LoanPageDTO(List<LoanDTO> items, PagingDTO paging){
        this.items = items;
        this.paging = paging;
    }

    public List<LoanDTO> getItems() {
        return items;
    }

    public PagingDTO getPaging() {
        return paging;
    }
}
