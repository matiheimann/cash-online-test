package com.cash.online.CashOnline.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "loans")
public class Loan {

    public Loan() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private int total;

    @ManyToOne(fetch = FetchType.LAZY)
    private DaoUser user;

    public long getId() {
        return id;
    }

    public DaoUser getUser() {
        return user;
    }

    public int getTotal() {
        return total;
    }
}
