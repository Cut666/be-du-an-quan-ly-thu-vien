package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.models.fileenum.StatusProduct;
import com.ntt.spring.security.login.models.fileenum.StatusProductNew;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.ProductRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.repository.WarehouseProductRepository;
import com.ntt.spring.security.login.repository.WarehouseRepository;
import com.ntt.spring.security.login.security.services.itp.ProductService;
import com.ntt.spring.security.login.security.services.itp.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

import static com.ntt.spring.security.login.models.fileenum.StatusProduct.available;
import static com.ntt.spring.security.login.models.fileenum.StatusProduct.not_available;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository libraryRepository;
    @Autowired
    private UnitService unitService;
    @Autowired
private WarehouseProductRepository warehouseProductRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Override
    public Object getByName(long id,String name) {
        List<Product> product = productRepository.findByUserIdAndProductNameContaining(id,name);
            return product;
    }

    @Override
    public Object getByCode1(long id, String name) {
        if (name==null){
            return null;
        }else {
            Product product = productRepository.findByUserIdAndCode(id,name);
            return product;
        }
    }
    @Override
    public Object getByCodeRental(long id, String name) {
        StatusProductNew statusProductNew = StatusProductNew.old;
        if (name==null){
            return null;
        }else {
            Product product = productRepository.findByUserIdAndCodeAndStatusProductNew(id,name,statusProductNew);
            return product;
        }
    }
    @Override
    public Object getAllByRental(long id) {
        StatusProductNew statusProductNew = StatusProductNew.old;
            List<Product> product = productRepository.findByUserIdAndStatusProductNew(id,statusProductNew);
            return product;
    }

    @Override
    public List<Product> getAll(long id) {
        List<Product> products = productRepository.findByUserId(id);
        Collections.sort(products, Comparator.comparingLong(Product::getId).reversed());
        return products;
    }

    @Override
    public Object creatProduct(Product product) {
        Product product2 = productRepository.findProductByCodeAndUser(product.getCode(),product.getUser());
        if (product2!=null){
            return new MessageResponse("Mã sản phẩm đã được tạo");
        }
        Product product1 = new Product();
        product1.setCode(product.getCode());
        product1.setName(product.getName());
        product1.setQuantity(product.getQuantity());
        product1.setPrice(product.getPrice());
        product1.setPriceRental(product.getPriceRental());
        User library = libraryRepository.findById(product.getUser().getId()).get();
        product1.setUser(library);
        product1.setStatusProductNew(product.getStatusProductNew());

        if (product.getQuantity() == 0) {
            product1.setStatusProduct(not_available);
        } else {
            product1.setStatusProduct(available);
        }
//        product1.setUnit(unitService.createUnitProduct(product.getUnit()));
        productRepository.save(product1);
        return new MessageResponse("tạo thành công");
    }

@Override
    public String generateCustomerCode(Long id) {
        List<Product> product = productRepository.findByUserId(id);
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        // Tạo 4 ký tự chữ ngẫu nhiên
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(letters.length());
            code.append(letters.charAt(index));
        }

        // Tạo 4 ký tự số ngẫu nhiên
        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        for (Product product1: product){
            if (product1.getCode()==code.toString()){
                return generateCustomerCode(id);
            }
        }
            return code.toString();
    }


    @Override
    public Object updateProduct(Product dto) {
        Product product = productRepository.findById(dto.getId()).get();
        Product product2 = productRepository.findProductByCodeAndUser(product.getCode(),product.getUser());
        if (product2 != null && product2.getId() != product.getId()) {
            return new MessageResponse("CCCD đã được đăng ký");
        }
        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setPriceRental(dto.getPriceRental());
        product.setStatusProductNew(dto.getStatusProductNew());
        if (dto.getQuantity() == 0) {
            product.setStatusProduct(not_available);
        } else {
            product.setStatusProduct(available);
        }
        productRepository.save(product);
        return new MessageResponse("Cập nhật thành công");
    }

    @Override
    public List<Product> updateQuantityProductReceipt(List<Product> p) {
        List<Product> updatedProducts = new ArrayList<>();
        for (Product productRQ : p) {
            Product product1 = productRepository.findByCode(productRQ.getCode());
            if (product1 == null) {
                Product product = new Product();
                product.setCode(product.getCode());
                product.setName(productRQ.getName());
                product.setQuantity(productRQ.getQuantity());
                product.setPrice(productRQ.getPrice());
                product.setPriceRental(productRQ.getPriceRental());
                if (productRQ.getQuantity() == 0) {
                    product.setStatusProduct(not_available);
                } else {
                    product.setStatusProduct(available);
                }
                product.setUnit(productRQ.getUnit());
                updatedProducts.add(productRepository.save(product));
            } else {
                product1.setCode(product1.getCode());
                product1.setName(product1.getName());
                product1.setQuantity(product1.getQuantity() + productRQ.getQuantity());
                product1.setPrice(product1.getPrice());
                product1.setPriceRental(product1.getPriceRental());
                if ((product1.getQuantity() + productRQ.getQuantity()) == 0) {
                    product1.setStatusProduct(not_available);
                } else {
                    product1.setStatusProduct(available);
                }
                updatedProducts.add(productRepository.save(product1));
            }
        }
        return updatedProducts;
    }

    @Override
    public List<Product> updateQuantityProductExport(List<Product> p) {
        List<Product> updatedProducts = new ArrayList<>();
        for (Product productRQ : p) {
            Product product1 = productRepository.findByCode(productRQ.getCode());
            if (product1 == null) {
                return null;
            } else {
                product1.setCode(product1.getCode());
                product1.setName(product1.getName());
                if (product1.getQuantity() - productRQ.getQuantity() < 0) {
                    return null;
                } else {
                    product1.setQuantity(product1.getQuantity() - productRQ.getQuantity());
                }
                if ((product1.getQuantity() - productRQ.getQuantity()) == 0) {
                    product1.setStatusProduct(not_available);
                } else {
                    product1.setStatusProduct(available);
                }
                updatedProducts.add(productRepository.save(product1));
            }
        }
        return updatedProducts;
    }

    @Override
    public Object deleteProduct(int id) {
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
        return new MessageResponse("tạo thành công");
    }

    @Override
    public Product updateProductForWarehouseProductExport(Product dto) {
        Product product = productRepository.findByCode(dto.getCode());
        if (product == null) {
            return null;
        } else {
            if (product.getQuantity() - dto.getQuantity() < 0){
                 throw new RuntimeException("sản phẩm khoong đủ");
            }else {
                product.setQuantity(product.getQuantity() - dto.getQuantity());
                if (product.getQuantity() - dto.getQuantity() == 0) {
                    product.setStatusProduct(not_available);
                } else if (product.getQuantity() - dto.getQuantity() > 0) {
                    product.setStatusProduct(available);
                }
                productRepository.save(product);
                return product;
            }
        }
    }

    @Override
    public Product updateProductForWarehouseProductReceipt(Product dto) {
        Product product = productRepository.findByCode(dto.getCode());
        if (product == null) {
            return null;
        } else {
                product.setQuantity(product.getQuantity() + dto.getQuantity());
                if (product.getQuantity() + dto.getQuantity() == 0) {
                    product.setStatusProduct(not_available);
                } else if (product.getQuantity() + dto.getQuantity() > 0) {
                    product.setStatusProduct(available);
                }
                productRepository.save(product);
                return product;
        }
    }
    @Override
    public List<WarehouseProduct> getWarehouseProductByConditionsin(Long userid, String code, LocalDate startDate, LocalDate endDate) {
        User user = libraryRepository.findById(userid).get();
        Product product = productRepository.findProductByCodeAndUser(code,user);
        List<Warehouse> warehouse1 =warehouseRepository.findAllByUserAndDateaddBetween(user, startDate, endDate);
        List<Warehouse> warehouse2 =warehouseRepository.findAllByUserAndExportdateBetween(user, startDate, endDate);
        warehouse1.addAll(warehouse2);
        List<WarehouseProduct> warehouseProducts = new ArrayList<>();
        for (Warehouse warehouse: warehouse1){
            List<WarehouseProduct> warehouseProducts1 = warehouse.getWarehouseProducts();
            warehouseProducts.addAll(warehouseProducts1);
        }
        List<WarehouseProduct> warehouseProducts1=new ArrayList<>();
        for (WarehouseProduct warehouseProduct:warehouseProducts){
                if (warehouseProduct.getProduct().getCode()==product.getCode()){
                    warehouseProducts1.add(warehouseProduct);
                }

        }
        return warehouseProducts1;
    }

    @Override
    public LocalDate getLastDayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        return lastDayOfMonth;
    }
    @Override
    public LocalDate getFirstDayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

        return firstDayOfMonth;
    }
    @Override
    public int getquantityProductByConditions(Long userid, String code,LocalDate startDate) {
        User user = libraryRepository.findById(userid).get();
        Product product = productRepository.findByUserIdAndCode(userid,code);
        List<Warehouse> warehouse1 =warehouseRepository.findAllByUserAndDateaddBefore(user, startDate);
        List<Warehouse> warehouse2 =warehouseRepository.findAllByUserAndExportdateBefore(user, startDate);
        int total1=0;
        int total2=0;
        List<WarehouseProduct> warehouseProducts = new ArrayList<>();
        for (Warehouse warehouse: warehouse1){
            List<WarehouseProduct> warehouseProducts1 = warehouse.getWarehouseProducts();
            warehouseProducts.addAll(warehouseProducts1);
        }
        for (WarehouseProduct warehouseProduct:warehouseProducts){
            if (warehouseProduct.getProduct().getCode()==product.getCode()){
                total1+=warehouseProduct.getQuantity();
            }
        }
        List<WarehouseProduct> warehouseProducts2 = new ArrayList<>();
        for (Warehouse warehouse: warehouse2){
            List<WarehouseProduct> warehouseProducts1 = warehouse.getWarehouseProducts();
            warehouseProducts2.addAll(warehouseProducts1);
        }
        for (WarehouseProduct warehouseProduct:warehouseProducts2){
            if (warehouseProduct.getProduct().getCode()==product.getCode()){
                total2+=warehouseProduct.getQuantity();
            }
        }
        return total1-total2;
    }
}
