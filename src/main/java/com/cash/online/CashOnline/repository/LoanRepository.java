package com.cash.online.CashOnline.repository;


import com.cash.online.CashOnline.model.DaoUser;
import com.cash.online.CashOnline.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends PagingAndSortingRepository<Loan, Long> {
    Page<Loan> findAllByUser(DaoUser user, Pageable pageable);
}
