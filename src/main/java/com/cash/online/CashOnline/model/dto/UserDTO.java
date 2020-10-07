package com.cash.online.CashOnline.model.dto;

import java.util.List;

public class UserDTO {

    private final long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final List<LoanDTO> loans;

    public UserDTO(long id, String email, String firstName, String lastName, List<LoanDTO> loans) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loans = loans;
    }
}
