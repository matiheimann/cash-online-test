package com.cash.online.CashOnline.repository;

import com.cash.online.CashOnline.model.DaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<DaoUser, Long> {
}
