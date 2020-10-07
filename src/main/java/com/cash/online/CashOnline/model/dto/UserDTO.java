package com.cash.online.CashOnline.model.dto;

import com.cash.online.CashOnline.model.DaoUser;

import java.util.Set;

public class UserDTO {

    private final long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Set<LoanDTO> loans;

    public UserDTO(long id, String email, String firstName, String lastName, Set<LoanDTO> loans) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loans = loans;
    }

    public UserDTO(DaoUser user, Set<LoanDTO> loans) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.loans = loans;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<LoanDTO> getLoans() {
        return loans;
    }
}
