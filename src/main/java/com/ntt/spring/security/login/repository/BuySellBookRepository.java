package com.ntt.spring.security.login.repository;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BuySellBookRepository extends JpaRepository<BuySellBook,Integer> {
    List<BuySellBook> findByStatusBuySell(StatusBuySell buy);

    BuySellBook findTopByOrderByIdDesc();

    BuySellBook findByCode(String code);

    List<BuySellBook> findByUserId(Long id);
    @Query("SELECT cf FROM BuySellBook cf WHERE cf.user.id = :userId AND cf.code LIKE %:code%")
    List<BuySellBook> findByUserIdAndCodeContaining(@Param("userId") Long userId, @Param("code") String code);

    List<BuySellBook> findByUserAndSubjectsAndStatusBuySellAndStatusPay(User user, Subjects subjects, StatusBuySell statusBuySell, StatusPay statusPay);

    List<BuySellBook> findByUserIdAndStatusBuySellAndPaymentDateBefore(Long userId, StatusBuySell status, LocalDate endDate);

    List<BuySellBook> findByUserIdAndStatusBuySellAndPaymentDateBetween(Long userId, StatusBuySell status, LocalDate startDate, LocalDate endDate);

    List<BuySellBook> findByUserAndSubjectsAndStatusBuySellAndStatusPayAndCollectionDateBefore(User user, Subjects subjects, StatusBuySell statusBuySell, StatusPay statusPay, LocalDate startDate);

    List<BuySellBook> findByUserAndSubjectsAndStatusBuySellAndStatusPayAndPaymentDateBefore(User user, Subjects subjects, StatusBuySell statusBuySell, StatusPay statusPay, LocalDate startDate);

    List<BuySellBook> findByUserAndSubjectsAndStatusBuySellAndStatusPayAndCollectionDateBetween(User user, Subjects subjects, StatusBuySell statusBuySell, StatusPay statusPay, LocalDate startDate, LocalDate endDate);

    List<BuySellBook> findByUserAndSubjectsAndStatusBuySellAndStatusPayAndPaymentDateBetween(User user, Subjects subjects, StatusBuySell statusBuySell, StatusPay statusPay, LocalDate startDate, LocalDate endDate);

    List<BuySellBook> findByUserIdAndStatusBuySell(Long id, StatusBuySell statusBuySell);
}
