package com.ntt.spring.security.login.repository;


import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.fileenum.StatusWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {
    Warehouse findByCode(String code);


    List<Warehouse> findByStatusWareHouse(StatusWarehouse warehouse);

    Warehouse findTopByOrderByIdDesc();

    List<Warehouse> findByUserId(long id);

    List<Warehouse> findByUserIdAndStatusWareHouse(Long id, StatusWarehouse status);


    List<Warehouse> findAllByUserAndDateaddBetween(User user, LocalDate startDate, LocalDate endDate);

    List<Warehouse> findAllByUserAndExportdateBetween(User user, LocalDate startDate, LocalDate endDate);

    List<Warehouse> findAllByUserAndDateaddBefore(User user, LocalDate startDate);

    List<Warehouse> findAllByUserAndExportdateBefore(User user, LocalDate startDate);
}
