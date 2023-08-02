package com.ntt.spring.security.login.repository;



import com.ntt.spring.security.login.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit,Integer> {
    Unit findByName(String name);
}
