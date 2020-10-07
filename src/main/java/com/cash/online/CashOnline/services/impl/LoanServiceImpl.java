package com.cash.online.CashOnline.services.impl;

import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.model.dto.LoanDTO;
import com.cash.online.CashOnline.repository.LoanRepository;
import com.cash.online.CashOnline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.cash.online.CashOnline.services.LoanService;

import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<LoanDTO> getLoans(Integer page, Integer size, Long userId) {
        if(userId == null) {
            this.loanRepository.findAll(PageRequest.of(page, size));
        }
        else {
            Optional<DaoUser> user = this.userRepository.findById(userId);
            if(!user.isPresent()) {
                return null;
            }
            this.loanRepository.findAllByUser(user.get(), PageRequest.of(page, size));
        }
        return null;
    }
}
