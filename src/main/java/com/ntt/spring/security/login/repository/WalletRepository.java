package com.ntt.spring.security.login.repository;



import com.ntt.spring.security.login.models.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Integer> {


    Wallet findByAccountNum(String accountNum);
}
