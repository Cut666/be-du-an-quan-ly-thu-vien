package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WarehouseProductService {
    public WarehouseProduct createWarehouseProductExport(WarehouseProduct warehouseProduct,Warehouse warehouse);
    public WarehouseProduct createWarehouseProductExportLostBook(WarehouseProduct warehouseProduct,Warehouse warehouse);
    public WarehouseProduct createWarehouseProductReceipt(WarehouseProduct warehouseProduct,Warehouse warehouse);
    public WarehouseProduct updateWarehouseProductExport(WarehouseProduct w,WarehouseProduct w2,Warehouse warehouse);
    public String deleteWarehouseProduct(int id);
    public List<WarehouseProduct> getWarehouseProductsByUserId(Long userId);
    public List<WarehouseProduct> getWarehouseProductsByUserIdAndWarehouseCode(Long userId, String warehouseCode);
    public List<WarehouseProduct> findWarehouseProductsByBuySellBook(int id);
    public List<WarehouseProduct> findWarehouseProductsByRental(int id);
    public List<WarehouseProduct> findWarehouseProductsByReRental(int id);
    public List<Product> findProductsByReRental(Long id, String code);
    public List<WarehouseProduct> findWarehouseProductsByReRental(Long id, String code);
}
