package com.ntt.spring.security.login.security.services;

import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.repository.WarehouseProductRepository;
import com.ntt.spring.security.login.repository.WarehouseRepository;
import com.ntt.spring.security.login.security.services.itp.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WarehouseProductRepository warehouseProductRepository;
    @Override
    public Warehouse getById(int id) {
        Warehouse warehouse = warehouseRepository.findById(id).get();
        return warehouse;
    }

    @Override
    public List<WarehouseProduct> getByIdWarehouse(int id) {
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouseId(id);
        return warehouseProducts;
    }

    @Override
    public List<Warehouse> getWarehousesByUserIdAndCode(Long userId, String warehouseCode) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Warehouse> warehouses = user.getWarehouses();
            List<Warehouse> result = new ArrayList<>();

            for (Warehouse warehouse : warehouses) {
                if (warehouse.getCode().equals(warehouseCode)) {
                    result.add(warehouse);
                }
            }

            return result;
        }

        return Collections.emptyList();
    }
}
