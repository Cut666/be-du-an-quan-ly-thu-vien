package com.ntt.spring.security.login.security.services;

import com.ntt.spring.security.login.models.dto.ReRentalDTO;
import com.ntt.spring.security.login.models.dto.RentalDTO;
import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.models.fileenum.StatusReRental;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.*;
import com.ntt.spring.security.login.security.services.itp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.ntt.spring.security.login.models.fileenum.StatusRental.notReturn;
import static com.ntt.spring.security.login.models.fileenum.StatusRental.returned;

@Component
@Transactional
public class ReRentalServiceImpl implements ReRentalService {
    @Autowired
    public RentalRepository rentalRepository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public SubjectsRepository subjectsRepository;
    @Autowired
    public ReceiptService receiptService;
    @Autowired
    public WarehouseRepository warehouseRepository;
    @Autowired
    public WarehouseProductRepository warehouseProductRepository;
    @Autowired
    public UserRepository libraryRepository;
    @Autowired
    public PhieuChiService phieuChiService;
    @Autowired
    public PhieuThuService phieuThuService;
    @Autowired
    @Qualifier("UNCServiceImpl")
    public UNCService uncService;
    @Autowired
    public BaoCoService baoCoService;
    @Autowired
    public CashFundRepository cashFundRepository;
    @Autowired
    public BanksRepository banksRepository;
    @Autowired
    public ReRentalRepository reRentalRepository;
    @Override
    public Object getByCode(Long id, String cccd) {
        return reRentalRepository.findByUserIdAndCodeContaining(id, cccd);
    }

    @Override
    public Object getById(int id) {
        return reRentalRepository.findById(id).get();
    }

    @Override
    public Object getAll(Long id) {
        List<ReRental> reRentals = reRentalRepository.findByUserId(id);
        Collections.sort(reRentals, Comparator.comparingLong(ReRental::getId).reversed());
        return reRentals;
    }

    @Override
    public Object creatReRental(ReRentalDTO dto, String cccd) {
        ReRental reRental = new ReRental();
        reRental.setCode(dto.getCode());
        LocalDate currentDate = LocalDate.now();
        reRental.setDateAdd(currentDate);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        reRental.setSubjects(subjects1);
        List<Rental> rentalList = rentalRepository.findByUserIdAndCodeContaining(dto.getUser().getId(),dto.getRental().getCode());
        Rental rental = rentalList.get(0);
        rental.setStatusRental(returned);
        rentalRepository.save(rental);
        reRental.setDateRe(dto.getDateRe());
        long numberDate = numberdate(rental.getTimeOrder(),dto.getDateRe());
        reRental.setDayNumber(numberDate);
        reRental.setNote(dto.getNote());
        Warehouse warehouse = receiptService.creatWarehouseReceiptforBuy(dto.getReceiptDTO(),cccd,dto.getUser().getId());
        warehouse.setDateadd(currentDate);
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        warehouse.setNote(dto.getNote());
        reRental.setRental(rental);
        reRental.setUser(user);
        reRental.setPenaltyRate(user.getRate());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        reRental.setWarehouse(warehouse);
        double moneyRental = total(warehouseProducts)*numberDate;
        reRental.setMoneyRental(moneyRental);
        long b;
        double totalPrice;
        double fine;
        if (numberDate>user.getDatemaxRental()){
            b = numberDate-user.getDatemaxRental();

        }else {
            b=0;
        }
        reRental.setNumberDateOut(b);
        fine = b* user.getRate()*total(warehouseProducts);
        reRental.setFine(fine);
        totalPrice=moneyRental+fine;
        reRental.setTotalprice(totalPrice);
        reRental.setStatusPay(StatusPay.noPay);
        reRentalRepository.save(reRental);
//        updateStatusReRental(dto.getCode(),dto.getUser().getId());
        return new MessageResponse("tạo phiếu trả sách thành công");
    }
    public double total(List<WarehouseProduct> warehouseProducts) {
        double total = 0;
        for (WarehouseProduct p : warehouseProducts) {
            total += p.getTotal();
        }
        return total;
    }
    @Override
    public Long numberdate(LocalDate a, LocalDate b){
        return ChronoUnit.DAYS.between(a,b);
    };

    @Override
    public Object updateReRental(ReRentalDTO dto, String cccd) {
        ReRental reRental = reRentalRepository.findById(dto.getId()).get();
        reRental.setCode(reRental.getCode());
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        reRental.setSubjects(subjects1);
        List<Rental> rentalList = rentalRepository.findByUserIdAndCodeContaining(dto.getUser().getId(),dto.getRental().getCode());
        Rental rental = rentalList.get(0);
        rental.setStatusRental(returned);
        rentalRepository.save(rental);
        reRental.setDateRe(dto.getDateRe());
        long numberDate = numberdate(rental.getTimeOrder(),dto.getDateRe());
        reRental.setDayNumber(numberDate);
        reRental.setNote(dto.getNote());
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        long b;
        if (numberDate>user.getDatemaxRental()){
            b = numberDate-user.getDatemaxRental();
        }else {
            b=0;
        }
        reRental.setNumberDateOut(b);
        Warehouse warehouse = receiptService.creatWarehouseReceiptforBuy(dto.getReceiptDTO(),cccd,dto.getUser().getId());
        warehouse.setDateadd(reRental.getDateAdd());
        warehouse.setNote(dto.getNote());
        reRental.setUser(user);
        reRental.setPenaltyRate(user.getRate());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        reRental.setWarehouse(warehouse);
        double moneyRental = total(warehouseProducts)*numberDate;
        reRental.setMoneyRental(moneyRental);
        double totalPrice;
        double fine;
        fine = b*user.getRate()*total(warehouseProducts);
        reRental.setFine(fine);
        totalPrice=moneyRental+fine;
        reRental.setTotalprice(totalPrice);
        reRental.setStatusPay(StatusPay.noPay);
        reRentalRepository.save(reRental);
//        updateStatusReRental(dto.getCode(),dto.getUser().getId());
        return new MessageResponse("cập nhật phiếu trả thành công");
    }


    @Override
    public Object deleteReRental(int id) {
        ReRental reRental = reRentalRepository.findById(id).get();
//        CashFund cashFund = cashFundRepository.findByReRental(reRental);
//        Banks banks = banksRepository.findByReRental(reRental);
//        if (cashFund!=null){
//            phieuChiService.deleteCashFund(cashFund.getId(),userid);
//        }
//        if (banks!=null){
//            uncService.deleteBanks(banks.getId(),userid);
//        }
        receiptService.deleteWarehouseReceipt(reRental.getWarehouse().getId());
        Rental rental = reRental.getRental();
        rental.setStatusRental(notReturn);
        rentalRepository.save(rental);
        reRentalRepository.delete(reRental);
        return new MessageResponse("xóa phiếu trả thành công");
    }
    @Override
    public String generateReceiptCode(Long id) {
        List<ReRental> rentals = reRentalRepository.findByUserId(id);
        if (rentals.isEmpty()) {
            return "RB0001";
        } else {
            ReRental rental = reRentalRepository.findTopByOrderByIdDesc();
            String code = rental.getCode();
//            char prefix = code.charAt(0);
//            char prefix1 = code.charAt(1);
            int suffix = Integer.parseInt(code.substring(2));
            if (suffix > 9999) {
                suffix = 1;
//                prefix++;
            } else {
                suffix++;
            }
            return String.format("RB%04d", suffix);
        }
    }
    @Override
    public Object updateRate(Long id, double d) {
        User user = libraryRepository.findById(id).get();
        user.setRate(d);
        libraryRepository.save(user);
        return new MessageResponse("câp nhập tỷ lệ thành công");
    }
    @Override
    public Object getRate(Long id) {
        User user = libraryRepository.findById(id).get();
        return user.getRate();
    }

    @Override
    public Object updateMaxDay(Long id, Long d) {
        User user = libraryRepository.findById(id).get();
        user.setDatemaxRental(d);
        libraryRepository.save(user);
        return new MessageResponse("câp nhập tối đa ngày thuê thành công");
    }

    @Override
    public Object getMaxDay(Long id) {
        User user = libraryRepository.findById(id).get();
        return user.getDatemaxRental();
    }

    @Override
    public LocalDate getDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate;
    }
    @Override
    public Object updateStatusReRental(String code,Long userid){
        List<Product> products = lostBook(code,userid);
        ReRental reRental = reRentalRepository.findByUserIdAndCode(userid,code);
        if (products.isEmpty()){
            reRental.setStatusReRental(StatusReRental.finished);
        }else {
            reRental.setStatusReRental(StatusReRental.unfinished);
        }
        return reRentalRepository.save(reRental);
    }
    @Override
    public List<Product> lostBook(String code,Long userid){
        List<ReRental> reRentalList = reRentalRepository.findByUserIdAndCodeContaining(userid,code);
        List<Product> productsRental = new ArrayList<>();
        List<Product> productsReRental = new ArrayList<>();
        List<Product> products= new ArrayList<>();
        ReRental reRental = reRentalList.get(0);
        List<WarehouseProduct> warehouseProduct1= reRental.getRental().getWarehouse().getWarehouseProducts();
        List<WarehouseProduct> warehouseProduct2 = reRental.getWarehouse().getWarehouseProducts();
        for (WarehouseProduct warehouseProduct: warehouseProduct1){
            Product product1 = warehouseProduct.getProduct();
            Product clonedProduct = new Product();
            clonedProduct.setId(product1.getId());
            clonedProduct.setQuantity(warehouseProduct.getQuantity());
            clonedProduct.setCode(product1.getCode());
            clonedProduct.setName(product1.getName());
            clonedProduct.setPrice(product1.getPrice());
            clonedProduct.setPriceRental(product1.getPriceRental());
            productsRental.add(clonedProduct);
        }
        for (WarehouseProduct warehouseProduct: warehouseProduct2){
            Product product2 = warehouseProduct.getProduct();
            Product clonedProduct = new Product();
            clonedProduct.setId(product2.getId());
            clonedProduct.setQuantity(warehouseProduct.getQuantity());
            clonedProduct.setCode(product2.getCode());
            clonedProduct.setName(product2.getName());
            clonedProduct.setPrice(product2.getPrice());
            clonedProduct.setPriceRental(product2.getPriceRental());
            productsReRental.add(clonedProduct);
        }
        for (Product product: productsRental){
            Product product1 = getProductByid(productsReRental,product.getId());
            if (product1==null){
                products.add(product);
            }else {
                int quantityDiff = product.getQuantity()-product1.getQuantity();
                if (quantityDiff>0){
                    product1.setQuantity(quantityDiff);
                    products.add(product1);
                }
            }
        }
        return products;
    }
    public Product getProductByid(List<Product> productList, int id) {
        for (Product product : productList) {
            if (product.getId()==id) {
                return product;
            }
        }
        return null;
    }
}
