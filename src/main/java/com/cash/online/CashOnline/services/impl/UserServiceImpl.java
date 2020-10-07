package com.cash.online.CashOnline.services.impl;

import com.cash.online.CashOnline.model.Loan;
import com.cash.online.CashOnline.model.dto.LoanDTO;
import com.cash.online.CashOnline.model.dto.UserDTO;
import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.repository.LoanRepository;
import com.cash.online.CashOnline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cash.online.CashOnline.services.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public UserServiceImpl(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public UserDTO getUserById(Long id) {
        DaoUser user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Set<LoanDTO> loans = user.getLoans()
                .stream()
                .map(loan -> new LoanDTO(loan))
                .collect(Collectors.toSet());
        return new UserDTO(user, loans);
    }
}
