package com.ntt.spring.security.login.repository;

import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.ReRental;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Rental;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReRentalRepository extends JpaRepository<ReRental, Integer> {
    @Query("SELECT cf FROM ReRental cf WHERE cf.user.id = :userId AND cf.code LIKE %:code%")
    List<ReRental> findByUserIdAndCodeContaining(@Param("userId") Long userId, @Param("code") String code);

    List<ReRental> findByUserId(Long id);

    ReRental findTopByOrderByIdDesc();

    Rental findByCode(String code);

    List<ReRental> findByUserAndSubjectsAndStatusPay(User user, Subjects subjects, StatusPay statusPay);

    List<ReRental> findByUserAndSubjectsAndStatusPayAndDateAddBefore(User user, Subjects subjects, StatusPay statusPay, LocalDate startDate);

    List<ReRental> findByUserAndSubjectsAndStatusPayAndDateAddBetween(User user, Subjects subjects, StatusPay statusPay, LocalDate startDate, LocalDate endDate);

    List<ReRental> findByUserIdAndDateAddBefore(Long userId, LocalDate startDate);

    List<ReRental> findByUserIdAndDateAddBetween(Long userId, LocalDate startDate, LocalDate endDate);

    ReRental findByRental(Rental rental);

    ReRental findByUserIdAndCode(Long userid, String code);
}
