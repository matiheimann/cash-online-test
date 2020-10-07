package com.cash.online.CashOnline.services.impl;

import com.cash.online.CashOnline.model.dto.UserDTO;
import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cash.online.CashOnline.services.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DaoUser getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
