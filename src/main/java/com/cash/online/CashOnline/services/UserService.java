package com.cash.online.CashOnline.services;

import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.model.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long id);
}
