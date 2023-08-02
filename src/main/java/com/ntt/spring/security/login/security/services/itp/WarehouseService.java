package com.ntt.spring.security.login.security.services.itp;

import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WarehouseService {
    public Warehouse getById(int id);
    public List<WarehouseProduct> getByIdWarehouse(int id);
    public List<Warehouse> getWarehousesByUserIdAndCode(Long userId, String warehouseCode);
}
