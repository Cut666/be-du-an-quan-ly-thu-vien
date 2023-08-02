package com.ntt.spring.security.login.repository;


import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.StatusProductNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByCode(String code);
    Product findTopByOrderByIdDesc();

    List<Product> findByUser(String tax);

    List<Product> findByUserId(Long id);
    @Query("SELECT p FROM Product p WHERE p.user.id = :userId AND p.name LIKE %:name%")
    List<Product> findByUserIdAndProductNameContaining(@Param("userId") Long userId, @Param("name") String name);
    @Query("SELECT p FROM Product p WHERE p.user.id = :userId AND p.code LIKE %:code%")
    Product findByUserIdAndProductCodeContaining(@Param("userId") Long userId, @Param("code") String code);

    Product findProductByCodeAndUser(String code, User user);

    Product findByUserIdAndCode(long id, String name);

    Product findByUserIdAndCodeAndStatusProductNew(long id, String name, StatusProductNew statusProductNew);

    List<Product> findByUserIdAndStatusProductNew(long id, StatusProductNew statusProductNew);
}
