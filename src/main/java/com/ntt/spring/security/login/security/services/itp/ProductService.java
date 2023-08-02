package com.ntt.spring.security.login.security.services.itp;



import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ProductService {
    public Object getByName(long id,String name);
    public Object getByCode1(long id,String name);
    public String generateCustomerCode(Long id);
    public Object getByCodeRental(long id, String name);
    public Object getAllByRental(long id);
    public List<Product> getAll(long id);
    public Object creatProduct(Product products);
    public Object updateProduct(Product dto);
    public Object deleteProduct(int id);
    public List<Product> updateQuantityProductReceipt(List<Product> products);
    public List<Product> updateQuantityProductExport(List<Product> products);
    public Product updateProductForWarehouseProductExport(Product dto);
    public Product updateProductForWarehouseProductReceipt(Product dto);
    public List<WarehouseProduct> getWarehouseProductByConditionsin(Long userid, String code, LocalDate startDate, LocalDate endDate);
    public LocalDate getLastDayOfMonth();
    public LocalDate getFirstDayOfMonth();
    public int getquantityProductByConditions(Long userid, String code,LocalDate startDate);
}
