package com.cash.online.CashOnline.model.dto;

import com.cash.online.CashOnline.model.Loan;

import java.io.Serializable;

public class LoanDTO {

    private final long id;
    private final double total;
    private final long userId;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.total = loan.getTotal();
        this.userId = loan.getUser().getId();
    }

    public LoanDTO(long id, double total, long userId){
       this.id = id;
       this.total = total;
       this.userId = userId;
   }

    public long getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public long getUserId() {
        return userId;
    }
}
