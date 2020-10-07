package com.cash.online.CashOnline.controllers;

import com.cash.online.CashOnline.model.Loan;
import com.cash.online.CashOnline.model.dto.LoanDTO;
import com.cash.online.CashOnline.model.dto.LoanPageDTO;
import com.cash.online.CashOnline.model.dto.LoanToAddDTO;
import com.cash.online.CashOnline.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("")
    public LoanPageDTO getLoansByPage(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "50") Integer size,
                                      @RequestParam(defaultValue = "") Long userId) {
        return this.loanService.getLoans(page, size, userId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLoan(@RequestBody LoanToAddDTO loan) {
        this.loanService.addLoan(loan);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable Long id){

    }

}
