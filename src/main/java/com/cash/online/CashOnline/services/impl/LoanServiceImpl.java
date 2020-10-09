package com.cash.online.CashOnline.services.impl;

import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.model.Loan;
import com.cash.online.CashOnline.model.dto.LoanDTO;
import com.cash.online.CashOnline.model.dto.LoanPageDTO;
import com.cash.online.CashOnline.model.dto.LoanToAddDTO;
import com.cash.online.CashOnline.model.dto.PagingDTO;
import com.cash.online.CashOnline.repository.LoanRepository;
import com.cash.online.CashOnline.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LoanPageDTO> getLoans(Integer page, Integer size, Long userId) {
        List<LoanDTO> loanPageDTOList;
        if(page == null || size == null || page <= 0 || size <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

        return new ResponseEntity(new LoanPageDTO(loanPageDTOList, new PagingDTO(loanPageDTOList.size(), page, totalElements)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LoanDTO> addLoan(LoanToAddDTO loan) {
        if(loan.getTotal() < 0) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<DaoUser> userOptional = this.userRepository.findById(loan.getUserId());
        if(!userOptional.isPresent()) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DaoUser user = userOptional.get();
        user.addLoan(new Loan(loan.getTotal(), user));
        DaoUser modifiedUser = this.userRepository.save(user);
        List<Loan> newLoans = new LinkedList<>(modifiedUser.getLoans());
        return new ResponseEntity<>(new LoanDTO(newLoans.get(newLoans.size() - 1)), HttpStatus.CREATED);
    }

    @Override
    public void deleteLoan(Long id) {
        this.loanRepository.deleteById(id);
    }
}
