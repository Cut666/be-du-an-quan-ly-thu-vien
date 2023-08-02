package com.ntt.spring.security.login.repository;


import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct,Integer> {
    WarehouseProduct findTopByOrderByIdDesc();

    List<WarehouseProduct> findByWarehouse(Warehouse warehouse);

    WarehouseProduct findByProduct(Product p);

    WarehouseProduct findByWarehouseAndProduct(Warehouse warehouse, Product product);

    WarehouseProduct findByProductAndWarehouse(Product product, Warehouse warehouse);

    List<WarehouseProduct> findByWarehouseId(int id);

    List<WarehouseProduct> findByWarehouseUser_Id(Long userId);
//    @Query("SELECT wp.quantity * wp.price AS total FROM WarehouseProduct wp")
//    List<Double> calculateTotal();
}
