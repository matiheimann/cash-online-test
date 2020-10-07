package com.cash.online.CashOnline.model.dto;

public class LoanToAddDTO {

    private final double total;
    private final Long userId;

    public LoanToAddDTO(double total, Long userId) {
        this.total = total;
        this.userId = userId;
    }

    public double getTotal() {
        return total;
    }

    public Long getUserId() {
        return userId;
    }
}
