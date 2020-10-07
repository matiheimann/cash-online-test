package com.cash.online.CashOnline.services.impl;

import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.model.Loan;
import com.cash.online.CashOnline.model.dto.LoanDTO;
import com.cash.online.CashOnline.model.dto.LoanPageDTO;
import com.cash.online.CashOnline.model.dto.LoanToAddDTO;
import com.cash.online.CashOnline.model.dto.PagingDTO;
import com.cash.online.CashOnline.repository.LoanRepository;
import com.cash.online.CashOnline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.cash.online.CashOnline.services.LoanService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LoanPageDTO getLoans(Integer page, Integer size, Long userId) {
        List<LoanDTO> loanPageDTOList;
        long totalElements;
        if(userId == null) {
            Page<Loan> pageLoan = this.loanRepository.findAll(PageRequest.of(page, size));
            totalElements = pageLoan.getTotalElements();
            loanPageDTOList = pageLoan.get()
                    .map(loan -> new LoanDTO(loan))
                    .collect(Collectors.toList());
        }
        else {
            Page<Loan> pageLoan = this.loanRepository.findAllByUserId(userId, PageRequest.of(page, size));
            totalElements = pageLoan.getTotalElements();
            loanPageDTOList = pageLoan.get()
                    .map(loan -> new LoanDTO(loan))
                    .collect(Collectors.toList());
        }

        return new LoanPageDTO(loanPageDTOList, new PagingDTO(loanPageDTOList.size(), page, totalElements));
    }

    @Override
    public LoanDTO addLoan(LoanToAddDTO loan) {
        if(loan.getTotal() < 0) {
            throw new RuntimeException("Invalid amount");
        }
        DaoUser user = this.userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new RuntimeException("User doesn't exist"));

        user.addLoan(new Loan(loan.getTotal(), user));
        this.userRepository.save(user);
        return null;
    }

    @Override
    public void deleteLoan(Long id) {
        this.loanRepository.deleteById(id);
    }
}
