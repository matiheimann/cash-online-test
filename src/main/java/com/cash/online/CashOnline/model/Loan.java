package com.cash.online.CashOnline.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "loans")
public class Loan {

    public Loan() {}

    public Loan(double total, DaoUser user) {
        this.total = total;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    private DaoUser user;

    public long getId() {
        return id;
    }

    public DaoUser getUser() {
        return user;
    }

    public double getTotal() {
        return total;
    }
}
