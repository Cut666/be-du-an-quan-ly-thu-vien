package com.ntt.spring.security.login.security.services;//package com.example.apiduan.service;

import com.ntt.spring.security.login.models.dto.ReceiptDTO;
import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import com.ntt.spring.security.login.models.fileenum.StatusWarehouse;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.ProductRepository;
import com.ntt.spring.security.login.repository.SubjectsRepository;
import com.ntt.spring.security.login.repository.WarehouseProductRepository;
import com.ntt.spring.security.login.repository.WarehouseRepository;
import com.ntt.spring.security.login.security.services.itp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Transactional
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    SubjectsRepository subjectsRepository;
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductService productService;
    @Autowired
    CustomerService customerService;
    @Autowired
    WarehouseProductService warehouseProductService;
    @Autowired
    WarehouseProductRepository warehouseProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public Object creatWarehouseReceipt(ReceiptDTO dto,String cccd) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(dto.getCode());
        warehouse.setDateadd(dto.getDateadd());
        warehouse.setNote(dto.getNote());
        warehouse.setStatusWareHouse(StatusWarehouse.warehouse);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        if (subjects1 == null) {
            throw new RuntimeException("đối tượng chưa được tạo");
        } else {
            warehouse.setSubjects(subjects1);
            warehouse.setUser(dto.getUser());
            for (WarehouseProduct p:dto.getWarehouseProducts()){
                Product product = productRepository.findByCode(p.getProduct().getCode());
                if (product==null){
                    throw new RuntimeException("chưa tạo sản phẩm");
                }
            }
            warehouseRepository.save(warehouse);
            List<WarehouseProduct> warehouseProducts = new ArrayList<>();
            for (WarehouseProduct wPQ : dto.getWarehouseProducts()){
                WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProductReceipt(wPQ,warehouse);
                    warehouseProducts.add(warehouseProduct);
            }
            return new MessageResponse("tạo phiếu nhập thành công");
        }
    }
    @Override
    public Warehouse creatWarehouseReceiptforBuy(ReceiptDTO dto,String cccd,Long id) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(generateReceiptCode(id));
        warehouse.setDateadd(dto.getDateadd());
        warehouse.setNote(dto.getNote());
        warehouse.setStatusWareHouse(StatusWarehouse.warehouse);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        if (subjects1 == null) {
            throw new RuntimeException("đối tượng chưa được tạo");
        } else {
            warehouse.setSubjects(subjects1);
            warehouse.setUser(dto.getUser());
            for (WarehouseProduct p:dto.getWarehouseProducts()){
                Product product = productRepository.findByCode(p.getProduct().getCode());
                if (product==null){
                    throw new RuntimeException("chưa tạo sản phẩm");
                }
            }
            warehouseRepository.save(warehouse);
            List<WarehouseProduct> warehouseProducts = new ArrayList<>();
            for (WarehouseProduct wPQ : dto.getWarehouseProducts()){
                WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProductReceipt(wPQ,warehouse);
                warehouseProducts.add(warehouseProduct);
            }
            return warehouse;
        }
    }

    @Override
    public Object updateWarehouseReceipt(ReceiptDTO dto,String cccd) {
        Warehouse warehouse = warehouseRepository.findById(dto.getId()).get();
        warehouse.setDateadd(dto.getDateadd());
        warehouse.setNote(dto.getNote());
        warehouse.setStatusWareHouse(StatusWarehouse.warehouse);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        if (subjects1 == null) {
            throw new RuntimeException("đối tượng chưa được tạo");
        } else {
            warehouse.setSubjects(subjects1);
            warehouse.setUser(dto.getUser());
            for (WarehouseProduct p : dto.getWarehouseProducts()) {
                Product product = productRepository.findByCode(p.getProduct().getCode());
                if (product == null) {
                    throw new RuntimeException("chưa tạo sản phẩm");
                }
            }
            warehouseRepository.save(warehouse);
            deleteWarehouseProduct(dto.getId());
            for (WarehouseProduct w : dto.getWarehouseProducts()){
                warehouseProductService.createWarehouseProductReceipt(w,warehouse);
            }
            return new MessageResponse("cập nhật thành công");
        }
    }
    @Override
    public Warehouse updateWarehouseReceiptForbuy(ReceiptDTO dto, String cccd, BuySellBook buySellBook) {
        Warehouse warehouse = buySellBook.getWarehouse();
        warehouse.setStatusWareHouse(StatusWarehouse.warehouse);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        if (subjects1 == null) {
            throw new RuntimeException("đối tượng chưa được tạo");
        } else {
            warehouse.setSubjects(subjects1);
            warehouse.setUser(dto.getUser());
            for (WarehouseProduct p : dto.getWarehouseProducts()) {
                Product product = productRepository.findByCode(p.getProduct().getCode());
                if (product == null) {
                    throw new RuntimeException("chưa tạo sản phẩm");
                }
            }
            warehouseRepository.save(warehouse);
            deleteWarehouseProduct(warehouse.getId());
            for (WarehouseProduct w : dto.getWarehouseProducts()){
                warehouseProductService.createWarehouseProductReceipt(w,warehouse);
            }
            return warehouse;
        }
    }
    public Object deleteWarehouseProduct(int id) {
        Warehouse warehouse =warehouseRepository.findById(id).get();
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);

        for (WarehouseProduct w: warehouseProducts) {
            int i = w.getQuantity();
            Product product = productRepository.findById(w.getProduct().getId()).get();
            product.setQuantity(product.getQuantity() - i);
            productRepository.save(product);
            warehouseProductRepository.delete(w);
        }
        return new MessageResponse("xóa thành công");
    }
    @Override
    public String generateReceiptCode(Long id) {
        StatusWarehouse status = StatusWarehouse.warehouse;
        List<Warehouse> warehouses =warehouseRepository.findByUserIdAndStatusWareHouse(id,  status);

        if (warehouses.isEmpty()) {
            return "NK0001";
        } else {
            Warehouse warehouse = warehouses.stream()
                    .max(Comparator.comparingInt(Warehouse::getId))
                    .orElse(null);
            String code = warehouse.getCode();
//            char prefix = code.charAt(0);
//            char prefix1 = code.charAt(1);
            int suffix = Integer.parseInt(code.substring(2));
            if (suffix > 9999) {
                suffix = 1;
//                prefix++;
            } else {
                suffix++;
            }
            return String.format("NK%04d", suffix);
        }
    }

    @Override
    public Object deleteWarehouseReceipt(int id) {
        Warehouse warehouse =warehouseRepository.findById(id).get();
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);

        for (WarehouseProduct w: warehouseProducts){
            int i = w.getQuantity();
            Product product = productRepository.findById(w.getProduct().getId()).get();
            product.setQuantity(product.getQuantity()-i);
            productRepository.save(product);
            warehouseProductRepository.delete(w);
        }
        warehouseRepository.delete(warehouse);
        return new MessageResponse("xóa thành công");
    }
}
