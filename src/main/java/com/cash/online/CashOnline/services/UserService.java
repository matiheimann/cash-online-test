package com.cash.online.CashOnline.services;

import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserDTO> getUserById(Long id);
    ResponseEntity<DaoUser> saveUser(DaoUser user);
    ResponseEntity<DaoUser> removeUser(Long userId);
}
