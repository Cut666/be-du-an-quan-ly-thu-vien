package com.ntt.spring.security.login.repository;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
import com.ntt.spring.security.login.models.fileenum.StatusBanks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BanksRepository extends JpaRepository<Banks,Integer> {
    Banks findByCode(String code);

    List<Banks> findByStatusBanks(StatusBanks UNC);

    Banks findTopByOrderByIdDesc();
    @Query("SELECT cf FROM Banks cf WHERE cf.user.id = :userId AND cf.code LIKE %:code%")
    List<Banks> findByUserIdAndCodeContaining(@Param("userId") Long userId, @Param("code") String code);

    List<Banks> findByUserId(Long id);

    Banks findByBuySellBook(BuySellBook buySellBook);

    Banks findByRental(Rental rental);

    Banks findByReRental(ReRental reRental);

    List<Banks> findByUserAndSubjectsAndStatusBanks(User user, Subjects subjects, StatusBanks statusBanks);

    List<Banks> findByUserAndSubjectsAndStatusBanksAndCollectionDateBefore(User user, Subjects subjects, StatusBanks statusBanks, LocalDate startDate);

    List<Banks> findByUserAndSubjectsAndStatusBanksAndPaymentDateBefore(User user, Subjects subjects, StatusBanks statusBanks, LocalDate startDate);

    List<Banks> findByUserAndSubjectsAndStatusBanksAndCollectionDateBetween(User user, Subjects subjects, StatusBanks statusBanks, LocalDate startDate, LocalDate endDate);

    List<Banks> findByUserAndSubjectsAndStatusBanksAndPaymentDateBetween(User user, Subjects subjects, StatusBanks statusBanks, LocalDate startDate, LocalDate endDate);

    List<Banks> findByUserIdAndStatusBanks(Long id, StatusBanks statusBanks);
}
