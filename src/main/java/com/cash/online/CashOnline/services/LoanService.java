package com.cash.online.CashOnline.services;

import com.cash.online.CashOnline.model.dto.LoanDTO;
import com.cash.online.CashOnline.model.dto.LoanPageDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {
    LoanPageDTO getLoans(Integer page, Integer size, Long userId);
}
