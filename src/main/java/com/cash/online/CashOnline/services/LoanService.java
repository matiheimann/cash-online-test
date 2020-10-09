package com.cash.online.CashOnline.services;

import com.cash.online.CashOnline.model.Loan;
import com.cash.online.CashOnline.model.dto.LoanDTO;
import com.cash.online.CashOnline.model.dto.LoanPageDTO;
import com.cash.online.CashOnline.model.dto.LoanToAddDTO;
import org.springframework.http.ResponseEntity;

public interface LoanService {
    ResponseEntity<LoanPageDTO> getLoans(Integer page, Integer size, Long userId);
    ResponseEntity<LoanDTO> addLoan(LoanToAddDTO loan);
    ResponseEntity<Loan> deleteLoan(Long id);
}
