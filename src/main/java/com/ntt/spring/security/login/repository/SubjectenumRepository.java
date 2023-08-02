package com.ntt.spring.security.login.repository;



import com.ntt.spring.security.login.models.entity.Subjectenum;
import com.ntt.spring.security.login.models.fileenum.Sub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectenumRepository extends JpaRepository<Subjectenum,Integer> {
    Subjectenum findBySub(Sub supplier);
}
