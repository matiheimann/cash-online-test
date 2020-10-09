package com.cash.online.CashOnline.services.impl;

import com.cash.online.CashOnline.model.dto.LoanDTO;
import com.cash.online.CashOnline.model.dto.UserDTO;
import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.repository.LoanRepository;
import com.cash.online.CashOnline.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cash.online.CashOnline.services.UserService;

import java.util.Optional;
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
    public ResponseEntity<UserDTO> getUserById(Long id) {
        Optional<DaoUser> user = this.userRepository.findById(id);
        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<LoanDTO> loans = user.get().getLoans()
                .stream()
                .map(loan -> new LoanDTO(loan))
                .collect(Collectors.toSet());
        return new ResponseEntity<>(new UserDTO(user.get(), loans), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DaoUser> saveUser(DaoUser user) {
        if(this.userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DaoUser> removeUser(Long userId) {
        Optional<DaoUser> user = this.userRepository.findById(userId);
        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.userRepository.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
