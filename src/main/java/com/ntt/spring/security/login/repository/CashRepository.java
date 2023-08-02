package com.ntt.spring.security.login.repository;


import com.ntt.spring.security.login.models.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends JpaRepository<Cash,Integer> {
}
