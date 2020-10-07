package com.cash.online.CashOnline.controllers;


import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public DaoUser getUserById(@PathVariable Long id){
        return this.userService.getUserById(id);
    }
}
