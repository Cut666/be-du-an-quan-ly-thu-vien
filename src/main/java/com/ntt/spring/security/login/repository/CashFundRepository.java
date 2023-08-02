package com.ntt.spring.security.login.repository;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.ReRental;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Rental;
import com.ntt.spring.security.login.models.fileenum.StatusCashFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CashFundRepository extends JpaRepository<CashFund,Integer> {
    CashFund findByCode(String code);

    List<CashFund> findByStatusCashFund(StatusCashFund phieuThu);

    CashFund findTopByOrderByIdDesc();

    List<CashFund> findByUserId(Long id);
    @Query("SELECT cf FROM CashFund cf WHERE cf.user.id = :userId AND cf.code LIKE %:code%")
    List<CashFund> findByUserIdAndCodeContaining(@Param("userId") Long userId, @Param("code") String code);


    CashFund findByBuySellBook(BuySellBook buySellBook);

    CashFund findByRental(Rental rental);

    CashFund findByReRental(ReRental reRental);

    List<CashFund> findByUserAndSubjectsAndStatusCashFund(User user, Subjects subjects, StatusCashFund statusCashFund);

    List<CashFund> findByUserAndSubjectsAndStatusCashFundAndPaymentDateBefore(User user, Subjects subjects, StatusCashFund statusCashFund, LocalDate startDate);

    List<CashFund> findByUserAndSubjectsAndStatusCashFundAndCollectionDateBefore(User user, Subjects subjects, StatusCashFund statusCashFund, LocalDate startDate);

    List<CashFund> findByUserAndSubjectsAndStatusCashFundAndPaymentDateBetween(User user, Subjects subjects, StatusCashFund statusCashFund, LocalDate startDate, LocalDate endDate);

    List<CashFund> findByUserAndSubjectsAndStatusCashFundAndCollectionDateBetween(User user, Subjects subjects, StatusCashFund statusCashFund, LocalDate startDate, LocalDate endDate);

    List<CashFund> findByUserIdAndStatusCashFund(Long id, StatusCashFund statusCashFund);
}
