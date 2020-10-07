package com.cash.online.CashOnline.services;

import com.cash.online.CashOnline.model.dto.LoanDTO;
import org.springframework.data.domain.Page;

public interface LoanService {
    Page<LoanDTO> getLoans(Integer page, Integer size, Long userId);
}
