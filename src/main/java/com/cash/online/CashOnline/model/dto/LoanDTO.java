package com.cash.online.CashOnline.model.dto;

public class LoanDTO {

    private final long id;
    private final int total;
    private final long userId;

   public LoanDTO(long id, int total, long userId){
       this.id = id;
       this.total = total;
       this.userId = userId;
   }
}
