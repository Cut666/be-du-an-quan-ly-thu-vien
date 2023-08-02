package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
import com.ntt.spring.security.login.repository.*;
import com.ntt.spring.security.login.security.services.itp.ProductService;
import com.ntt.spring.security.login.security.services.itp.WarehouseProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ntt.spring.security.login.models.fileenum.StatusProduct.available;
import static com.ntt.spring.security.login.models.fileenum.StatusProduct.not_available;


@Component
public class WarehouseProductServiceImpl implements WarehouseProductService {
    @Autowired
    WarehouseProductRepository warehouseProductRepository;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BuySellBookRepository buySellBookRepository;
    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    ReRentalRepository reRentalRepository;

    @Override
    public WarehouseProduct createWarehouseProductExport(WarehouseProduct warehouseProduct,Warehouse warehouse) {
        WarehouseProduct warehouseProduct1 = new WarehouseProduct();
        warehouseProduct1.setQuantity(warehouseProduct.getProduct().getQuantity());
        if (warehouseProduct.getProduct().getPrice()==0){
            Product product = productRepository.findByUserIdAndProductCodeContaining(warehouse.getUser().getId(), warehouseProduct.getProduct().getCode());
            warehouseProduct1.setPrice(product.getPrice());
            warehouseProduct1.setTotal(warehouseProduct.getProduct().getQuantity()*product.getPrice());
        }else {
            warehouseProduct1.setPrice(warehouseProduct.getProduct().getPrice());
            warehouseProduct1.setTotal(warehouseProduct.getProduct().getQuantity()*warehouseProduct.getProduct().getPrice());
        }

        Product p = productService.updateProductForWarehouseProductExport(warehouseProduct.getProduct());
        if (p ==null){
            return null;
        }else {
            warehouseProduct1.setWarehouse(warehouse);
            warehouseProduct1.setProduct(p);
            warehouseProductRepository.save(warehouseProduct1);
            return warehouseProduct1;
        }

    }

    @Override
    public WarehouseProduct createWarehouseProductExportLostBook(WarehouseProduct warehouseProduct, Warehouse warehouse) {
        WarehouseProduct warehouseProduct1 = new WarehouseProduct();
        warehouseProduct1.setQuantity(warehouseProduct.getProduct().getQuantity());
        if (warehouseProduct.getProduct().getPrice()==0){
            Product product = productRepository.findByUserIdAndProductCodeContaining(warehouse.getUser().getId(), warehouseProduct.getProduct().getCode());
            warehouseProduct1.setPrice(product.getPrice());
            warehouseProduct1.setTotal(warehouseProduct.getProduct().getQuantity()*product.getPrice());
        }else {
            warehouseProduct1.setPrice(warehouseProduct.getProduct().getPrice());
            warehouseProduct1.setTotal(warehouseProduct.getProduct().getQuantity()*warehouseProduct.getProduct().getPrice());
        }

        Product p = productRepository.findByUserIdAndProductCodeContaining(warehouse.getUser().getId(), warehouseProduct.getProduct().getCode());
            warehouseProduct1.setWarehouse(warehouse);
            warehouseProduct1.setProduct(p);
            warehouseProductRepository.save(warehouseProduct1);
            return warehouseProduct1;
    }

    @Override
    public WarehouseProduct createWarehouseProductReceipt(WarehouseProduct warehouseProduct,Warehouse warehouse) {
        WarehouseProduct warehouseProduct1 = new WarehouseProduct();
        warehouseProduct1.setQuantity(warehouseProduct.getProduct().getQuantity());
        if (warehouseProduct.getProduct().getPrice()==0){
            Product product = productRepository.findByUserIdAndProductCodeContaining(warehouse.getUser().getId(), warehouseProduct.getProduct().getCode());
            warehouseProduct1.setPrice(product.getPriceRental());
            warehouseProduct1.setTotal(warehouseProduct.getProduct().getQuantity()*product.getPriceRental());
        }else {
            warehouseProduct1.setPrice(warehouseProduct.getProduct().getPrice());
            warehouseProduct1.setTotal(warehouseProduct.getProduct().getQuantity()*warehouseProduct.getProduct().getPrice());
        }
        Product p = productService.updateProductForWarehouseProductReceipt(warehouseProduct.getProduct());
        if (p ==null){
            return null;
        }else {
            warehouseProduct1.setWarehouse(warehouse);
            warehouseProduct1.setProduct(p);
            warehouseProductRepository.save(warehouseProduct1);
            return warehouseProduct1;
        }

    }


    @Override
    public WarehouseProduct updateWarehouseProductExport(WarehouseProduct warehouseProduct, WarehouseProduct w2,Warehouse warehouse) {
        if (warehouseProduct == null) {
            WarehouseProduct warehouseProduct1 = createWarehouseProductExport(warehouseProduct,warehouse);
            return warehouseProduct1;
        } else {
            int i = warehouseProduct.getQuantity() - w2.getProduct().getQuantity();
            warehouseProduct.setQuantity(w2.getProduct().getQuantity());
            warehouseProduct.setPrice(w2.getProduct().getPrice());
            warehouseProduct.setProduct(w2.getProduct());
            Product product = productRepository.findByCode(w2.getProduct().getCode());
            if (product.getQuantity() + i < 0) {
                throw new RuntimeException("sản phẩm không đủ");
            } else {
                product.setQuantity(product.getQuantity() + i);
                if (product.getQuantity() + i == 0) {
                    product.setStatusProduct(not_available);
                } else if (product.getQuantity() + i > 0) {
                    product.setStatusProduct(available);
                }
                productRepository.save(product);
                warehouseProductRepository.save(warehouseProduct);
                return warehouseProduct;
            }

        }
    }

    @Override
    public String deleteWarehouseProduct(int id) {
        WarehouseProduct warehouseProduct = warehouseProductRepository.findById(id).get();
        warehouseProductRepository.delete(warehouseProduct);
        return "xoa thanh cong";
    }

    @Override
    public List<WarehouseProduct> getWarehouseProductsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Warehouse> warehouses = user.getWarehouses();
            List<WarehouseProduct> warehouseProducts = new ArrayList<>();

            for (Warehouse warehouse : warehouses) {
                List<WarehouseProduct> products = warehouse.getWarehouseProducts();
                warehouseProducts.addAll(products);
            }
            return warehouseProducts;
        }
        return Collections.emptyList();
    }

    @Override
    public List<WarehouseProduct> getWarehouseProductsByUserIdAndWarehouseCode(Long userId, String warehouseCode) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Warehouse> warehouses = user.getWarehouses();
            List<WarehouseProduct> warehouseProducts = new ArrayList<>();

            for (Warehouse warehouse : warehouses) {
                if (warehouse.getCode().equals(warehouseCode)) {
                    List<WarehouseProduct> products = warehouse.getWarehouseProducts();
                    warehouseProducts.addAll(products);
                }
            }

            return warehouseProducts;
        }
        return Collections.emptyList();
    }

    @Override
    public List<WarehouseProduct> findWarehouseProductsByBuySellBook(int id) {
        BuySellBook buySellBook = buySellBookRepository.findById(id).get();
        Warehouse warehouse = buySellBook.getWarehouse();
        if (warehouse != null) {
            List<WarehouseProduct> warehouseProducts = warehouse.getWarehouseProducts();
            return warehouseProducts;
        }
        return new ArrayList<>();
    }
    @Override
    public List<WarehouseProduct> findWarehouseProductsByRental(int id) {
        Rental rental = rentalRepository.findById(id).get();
        Warehouse warehouse = rental.getWarehouse();
        if (warehouse != null) {
            List<WarehouseProduct> warehouseProducts = warehouse.getWarehouseProducts();
            return warehouseProducts;
        }
        return new ArrayList<>();
    }

    @Override
    public List<WarehouseProduct> findWarehouseProductsByReRental(int id) {
        ReRental rental = reRentalRepository.findById(id).get();
        Warehouse warehouse = rental.getWarehouse();
        if (warehouse != null) {
            List<WarehouseProduct> warehouseProducts = warehouse.getWarehouseProducts();
            return warehouseProducts;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Product> findProductsByReRental(Long id,String code) {
        List<Rental> rentallist = rentalRepository.findByUserIdAndCodeContaining(id, code);
        Rental rental = rentallist.get(0);
        Warehouse warehouse = rental.getWarehouse();
        if (warehouse != null) {
            List<WarehouseProduct> warehouseProducts = warehouse.getWarehouseProducts();
            List<Product> products = new ArrayList<>();
            for (WarehouseProduct w:warehouseProducts){
                Product product = w.getProduct();
                products.add(product);
            }
            return products;
        }
        return new ArrayList<>();
    }

    @Override
    public List<WarehouseProduct> findWarehouseProductsByReRental(Long id, String code) {
        List<Rental> rentallist = rentalRepository.findByUserIdAndCodeContaining(id, code);
        Rental rental = rentallist.get(0);
        Warehouse warehouse = rental.getWarehouse();
        if (warehouse != null) {
            List<WarehouseProduct> warehouseProducts = warehouse.getWarehouseProducts();
           return warehouseProducts;
        }
        return new ArrayList<>();
    }

}
