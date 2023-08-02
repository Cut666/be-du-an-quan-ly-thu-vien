package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.dto.ExportDTO;
import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@Transactional
public class ExportServiceImpl implements ExportService {
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
    public Warehouse getByCode(String code) {
        Warehouse warehouse = warehouseRepository.findByCode(code);
        if (warehouse == null) {
            return null;
        } else {
            return warehouse;
        }
    }

    @Override
    public List<Warehouse> getAll(long id) {
        List<Warehouse> warehouse = warehouseRepository.findByUserId(id);
        Collections.sort(warehouse, Comparator.comparingLong(Warehouse::getId).reversed());
        return warehouse;
    }

    @Override
    public Object creatWarehouse(ExportDTO dto,String cccd) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(dto.getCode());
        warehouse.setExportdate(dto.getExportdate());
        warehouse.setNote(dto.getNote());
        warehouse.setStatusWareHouse(StatusWarehouse.export);
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
            else if(product.getQuantity()-p.getProduct().getQuantity()<0){
                throw new RuntimeException("sản phẩm không đủ");
            }
        }
            warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = new ArrayList<>();
        for (WarehouseProduct wPQ : dto.getWarehouseProducts()){
            WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProductExport(wPQ,warehouse);
            if(warehouseProduct==null){
                return new MessageResponse("san pham khong du");

            }else {

                warehouseProducts.add(warehouseProduct);
            }
        }
        return new MessageResponse("tạo phiếu xuất thành công");
    }
    }

    @Override
    public Object updateWarehouse(ExportDTO dto,String cccd) {
        Warehouse warehouse = warehouseRepository.findById(dto.getId()).get();
            warehouse.setExportdate(dto.getExportdate());
            warehouse.setNote(dto.getNote());
            warehouse.setStatusWareHouse(StatusWarehouse.export);
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
                } else if (product.getQuantity() - p.getProduct().getQuantity() < 0) {
                    throw new RuntimeException("sản phẩm không đủ");
                }
            }
            warehouseRepository.save(warehouse);
            deleteWarehouseProduct(dto.getId());
                        for (WarehouseProduct w : dto.getWarehouseProducts()){
                    warehouseProductService.createWarehouseProductExport(w,warehouse);
            }
            return new MessageResponse("cập nhật thành công");
        }
    }
    public Object deleteWarehouseProduct(int id) {
        Warehouse warehouse =warehouseRepository.findById(id).get();
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);

        for (WarehouseProduct w: warehouseProducts) {
            int i = w.getQuantity();
            Product product = productRepository.findById(w.getProduct().getId()).get();
            product.setQuantity(product.getQuantity() + i);
            productRepository.save(product);
            warehouseProductRepository.delete(w);
        }
        return new MessageResponse("xóa thành công");
    }
    @Override
    public Object deleteWarehouseExport(int id) {
        Warehouse warehouse =warehouseRepository.findById(id).get();
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);

        for (WarehouseProduct w: warehouseProducts){
            int i = w.getQuantity();
            Product product = productRepository.findById(w.getProduct().getId()).get();
            product.setQuantity(product.getQuantity()+i);
            productRepository.save(product);
            warehouseProductRepository.delete(w);
        }
        warehouseRepository.delete(warehouse);
        return new MessageResponse("xóa thành công");
    }

    @Override
    public Warehouse creatWarehouseForSell(ExportDTO dto, String cccd,Long id) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(generateReceiptCode(id));
        warehouse.setExportdate(dto.getExportdate());
        warehouse.setNote(dto.getNote());
        warehouse.setStatusWareHouse(StatusWarehouse.export);
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
                else if(product.getQuantity()-p.getProduct().getQuantity()<0){
                    throw new RuntimeException("sản phẩm không đủ");
                }
            }
            warehouseRepository.save(warehouse);
            List<WarehouseProduct> warehouseProducts = new ArrayList<>();
            for (WarehouseProduct wPQ : dto.getWarehouseProducts()){
                WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProductExport(wPQ,warehouse);
                if(warehouseProduct==null){
                    throw new RuntimeException("sản phẩm không đủ");

                }else {

                    warehouseProducts.add(warehouseProduct);
                }
            }
            return warehouse;
        }
    }

    @Override
    public Warehouse creatWarehouseForSellLostBook(ExportDTO dto, String cccd,Long id) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(generateReceiptCode(id));
        warehouse.setExportdate(dto.getExportdate());
        warehouse.setNote(dto.getNote());
        warehouse.setStatusWareHouse(StatusWarehouse.export);
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
                else if(product.getQuantity()-p.getProduct().getQuantity()<0){
                    throw new RuntimeException("sản phẩm không đủ");
                }
            }
            warehouseRepository.save(warehouse);
            List<WarehouseProduct> warehouseProducts = new ArrayList<>();
            for (WarehouseProduct wPQ : dto.getWarehouseProducts()){
                WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProductExportLostBook(wPQ,warehouse);
                if(warehouseProduct==null){
                    throw new RuntimeException("sản phẩm không đủ");

                }else {

                    warehouseProducts.add(warehouseProduct);
                }
            }
            return warehouse;
        }
    }

    @Override
    public Warehouse updateWarehouseForSell(ExportDTO dto, String cccd, BuySellBook buySellBook) {
        Warehouse warehouse = buySellBook.getWarehouse();
        deleteWarehouseProduct(warehouse.getId());
        warehouse.setStatusWareHouse(StatusWarehouse.export);
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
                } else if (product.getQuantity() - p.getProduct().getQuantity() < 0) {
                    throw new RuntimeException("sản phẩm không đủ");
                }
            }
            warehouseRepository.save(warehouse);
            for (WarehouseProduct w : dto.getWarehouseProducts()){
                warehouseProductService.createWarehouseProductExport(w,warehouse);
            }
            return warehouse;
        }
    }
    @Override
    public Warehouse updateWarehouseForRental(ExportDTO dto, String cccd, Rental rental) {
        Warehouse warehouse = rental.getWarehouse();
        deleteWarehouseProduct(warehouse.getId());
        warehouse.setStatusWareHouse(StatusWarehouse.export);
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
                } else if (product.getQuantity() - p.getProduct().getQuantity() < 0) {
                    throw new RuntimeException("sản phẩm không đủ");
                }
            }
            warehouseRepository.save(warehouse);

            for (WarehouseProduct w : dto.getWarehouseProducts()){
                warehouseProductService.createWarehouseProductExport(w,warehouse);
            }
            return warehouse;
        }
    }
    @Override
    public String generateReceiptCode(Long id) {
        StatusWarehouse status = StatusWarehouse.export;
        List<Warehouse> warehouses =warehouseRepository.findByUserIdAndStatusWareHouse(id,  status);
        if (warehouses.isEmpty()) {
            return "XK0001";
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
            return String.format("XK%04d", suffix);
        }
    }
}
