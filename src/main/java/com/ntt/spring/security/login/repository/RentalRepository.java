package com.ntt.spring.security.login.repository;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Rental;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Integer> {

    List<Rental> findByUserId(Long id);
    @Query("SELECT cf FROM Rental cf WHERE cf.user.id = :userId AND cf.code LIKE %:code%")
    List<Rental> findByUserIdAndCodeContaining(@Param("userId") Long userId, @Param("code") String code);

    Rental findTopByOrderByIdDesc();

    List<Rental> findByUserAndSubjectsAndStatusPay(User user, Subjects subjects, StatusPay statusPay);

    Rental findByUserIdAndCode(Long id, String cccd);
}
