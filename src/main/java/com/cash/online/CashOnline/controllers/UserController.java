package com.cash.online.CashOnline.controllers;


import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.model.dto.UserDTO;
import com.cash.online.CashOnline.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return this.userService.getUserById(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<DaoUser> newUser(@RequestBody DaoUser newUser) { return this.userService.saveUser(newUser);}

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) { this.userService.removeUser(id); }
}
