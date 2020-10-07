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
    private double total;

    @ManyToOne
    private DaoUser user;
}
