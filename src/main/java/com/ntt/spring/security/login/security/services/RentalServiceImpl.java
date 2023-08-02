package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.dto.RentalDTO;
import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.*;
import com.ntt.spring.security.login.security.services.itp.BaoCoService;
import com.ntt.spring.security.login.security.services.itp.ExportService;
import com.ntt.spring.security.login.security.services.itp.PhieuThuService;
import com.ntt.spring.security.login.security.services.itp.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.ntt.spring.security.login.models.fileenum.StatusRental.notReturn;
import static com.ntt.spring.security.login.models.fileenum.StatusRental.returned;


@Component
@Transactional
public class RentalServiceImpl implements RentalService {
    @Autowired
    public RentalRepository rentalRepository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public SubjectsRepository subjectsRepository;
    @Autowired
    public ExportService exportService;
    @Autowired
    public WarehouseRepository warehouseRepository;
    @Autowired
    public WarehouseProductRepository warehouseProductRepository;
    @Autowired
    public UserRepository libraryRepository;
    @Autowired
    public PhieuThuService phieuThuService;
    @Autowired
    public BaoCoService baoCoService;
    @Autowired
    public CashFundRepository cashFundRepository;
    @Autowired
    public BanksRepository banksRepository;
    @Autowired
    public ReRentalRepository reRentalRepository;

    @Override
    public Object getByCode(Long id,String cccd) {
        List<Rental> rental =rentalRepository.findByUserIdAndCodeContaining(id,cccd);
        ReRental reRental = reRentalRepository.findByRental(rental.get(0));
        if (reRental!=null){
            return new MessageResponse("Đã tồn tại phiếu trả sách cho phiếu thuê này");
        }
        return rental;
    }
    @Override
    public Object findByCode(Long id,String cccd) {
        return rentalRepository.findByUserIdAndCode(id,cccd);
    }

    @Override
    public Object getById(int id) {
        return rentalRepository.findById(id).get();
    }

    @Override
    public Object getAll(Long id) {
        List<Rental> rentals = rentalRepository.findByUserId(id);
        Collections.sort(rentals, Comparator.comparingLong(Rental::getId).reversed());
        return rentals;
    }

    @Override
    public Object creatRental(RentalDTO dto, String cccd) {
        Rental rental = new Rental();
        rental.setCode(dto.getCode());
        LocalDate currentDate = LocalDate.now();
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        rental.setSubjects(subjects1);
        rental.setTimeOrder(currentDate);
        rental.setDayNumber(dto.getDayNumber());
        rental.setEstimateTime(date(dto.getDayNumber()));
        rental.setNote(dto.getNote());
        Warehouse warehouse = exportService.creatWarehouseForSell(dto.getExportDTO(),cccd,dto.getUser().getId());
        warehouse.setExportdate(currentDate);
        warehouse.setNote(dto.getNote());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        double money = total(warehouseProducts);
        rental.setDepositPercentage(dto.getDepositPercentage());
        rental.setTotalprice(money*dto.getDepositPercentage());
        rental.setWarehouse(warehouse);
        rental.setStatusRental(notReturn);
        rental.setUser(user);
        rental.setStatusPay(dto.getStatusPay());
        if (dto.getStatusPay()== StatusPay.noPay){
            rentalRepository.save(rental);
            return new MessageResponse("tạo phiếu thuê thành công");
        }else {
            if (dto.getPhieuThuDTO()!=null){
                rentalRepository.save(rental);
                CashFund cashFund = phieuThuService.creatCashFundForSell(dto.getPhieuThuDTO(),cccd);
                cashFund.setPaymentDate(currentDate);
                cashFund.setNote(dto.getNote());
                cashFund.setMoney(money* dto.getDepositPercentage());
                cashFund.setRental(rental);
                cashFundRepository.save(cashFund);
                user.getCash().setBanlance(user.getCash().getBanlance()+money*dto.getDepositPercentage());
                libraryRepository.save(user);
            }else if (dto.getBaoCoDTO()!=null){
                rentalRepository.save(rental);
                Banks banks = baoCoService.createBanksForSell(dto.getBaoCoDTO(),cccd);
                banks.setPaymentDate(currentDate);
                banks.setNote(dto.getNote());
                banks.setMoney(money*dto.getDepositPercentage());
                banks.setRental(rental);
                banksRepository.save(banks);
                user.getWallet().setBalance(user.getWallet().getBalance()+money*dto.getDepositPercentage());
                libraryRepository.save(user);
            }
            return new MessageResponse("tạo phiếu thuê thành công");
        }
    }

    public LocalDate date(int number) {
        LocalDate currentDate = LocalDate.now();
        LocalDate estimateDate = currentDate.plusDays(number);
        return estimateDate;
    }

    public double total(List<WarehouseProduct> warehouseProducts) {
        double total = 0;
        for (WarehouseProduct p : warehouseProducts) {
            total += p.getTotal();
        }
        return total;
    }


    @Override
    public Object updateRental(RentalDTO dto,String cccd) {
        Rental rental = rentalRepository.findById(dto.getId()).get();
        rental.setTimeOrder(rental.getTimeOrder());
        rental.setDayNumber(dto.getDayNumber());
        rental.setEstimateTime(date(dto.getDayNumber()));
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        rental.setSubjects(subjects1);
        rental.setNote(dto.getNote());
        rental.setStatusRental(notReturn);
        Warehouse warehouse = exportService.updateWarehouseForRental(dto.getExportDTO(),cccd,rental);
        warehouse.setExportdate(rental.getTimeOrder());
        warehouse.setNote(dto.getNote());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        double money = total(warehouseProducts);
        rental.setDepositPercentage(dto.getDepositPercentage());
        rental.setTotalprice(money*dto.getDepositPercentage());
        rental.setWarehouse(warehouse);
        rental.setUser(user);
        rental.setStatusRental(notReturn);
        rental.setStatusPay(dto.getStatusPay());
        if (dto.getStatusPay() == StatusPay.noPay){

            CashFund cashFund = cashFundRepository.findByRental(rental);
            Banks banks =  banksRepository.findByRental(rental);
            if(cashFund!=null){
                user.getCash().setBanlance(user.getCash().getBanlance()-cashFund.getMoney());
                libraryRepository.save(user);
                cashFundRepository.delete(cashFund);
            }else if(banks!=null){
                user.getWallet().setBalance(user.getWallet().getBalance()-banks.getMoney());
                libraryRepository.save(user);
                banksRepository.delete(banks);
            }
            rentalRepository.save(rental);
            return new MessageResponse("cập nhật thành công");
        }else {
            CashFund cashFund1 = cashFundRepository.findByRental(rental);
            Banks banks1 =  banksRepository.findByRental(rental);
            if(cashFund1!=null){
                user.getCash().setBanlance(user.getCash().getBanlance()-cashFund1.getMoney());
                libraryRepository.save(user);
                cashFundRepository.delete(cashFund1);
            }else if(banks1!=null){
                user.getWallet().setBalance(user.getWallet().getBalance()-banks1.getMoney());
                libraryRepository.save(user);
                banksRepository.delete(banks1);
            }
            if (dto.getPhieuThuDTO()!=null){
                rentalRepository.save(rental);
                CashFund cashFund = phieuThuService.creatCashFundForSell(dto.getPhieuThuDTO(),cccd);
                cashFund.setPaymentDate(rental.getTimeOrder());
                cashFund.setNote(dto.getNote());
                cashFund.setMoney(money*dto.getDepositPercentage());
                cashFund.setRental(rental);
                cashFundRepository.save(cashFund);
                user.getCash().setBanlance(user.getCash().getBanlance()+money*dto.getDepositPercentage());
                libraryRepository.save(user);
            }else if (dto.getBaoCoDTO()!=null){
                rentalRepository.save(rental);
                Banks banks = baoCoService.createBanksForSell(dto.getBaoCoDTO(),cccd);
                banks.setPaymentDate(rental.getTimeOrder());
                banks.setNote(dto.getNote());
                banks.setMoney(money*dto.getDepositPercentage());
                banks.setRental(rental);
                banksRepository.save(banks);
                user.getWallet().setBalance(user.getWallet().getBalance()+money*dto.getDepositPercentage());
                libraryRepository.save(user);
            }
            return new MessageResponse("Cập nhật phiếu thuê thành công");
        }

        }

        @Override
        public Object deleteRental (int id, Long userid){
            Rental rental = rentalRepository.findById(id).get();
            ReRental reRental = reRentalRepository.findByRental(rental);
            if (reRental==null){
                CashFund cashFund = cashFundRepository.findByRental(rental);
                Banks banks = banksRepository.findByRental(rental);
                if (cashFund!=null){
                    phieuThuService.deleteCashFund(cashFund.getId(),userid);
                }
                if (banks!=null){
                    baoCoService.deleteBanks(banks.getId(),userid);
                }
                exportService.deleteWarehouseExport(rental.getWarehouse().getId());
                rentalRepository.delete(rental);
                return new MessageResponse("Xóa phiếu thuê thành công");
            }else {
                return new MessageResponse("Bạn cần phải xóa phiếu trả sách tương ứng trước");
            }

        }

    @Override
    public Object updateDepositPercentage(Long id, double d) {
        User user = libraryRepository.findById(id).get();
        user.setDepositPercentage(d);
        libraryRepository.save(user);
        return new MessageResponse("câp nhập tỷ lệ thành công");
    }
    @Override
    public Object getDepositPercentage(Long id) {
        User user = libraryRepository.findById(id).get();
        return user.getDepositPercentage();
    }
@Override
    public String generateReceiptCode(Long id) {
        List<Rental> rentals = rentalRepository.findByUserId(id);
        if (rentals.isEmpty()) {
            return "TS0001";
        } else {
            Rental rental = rentalRepository.findTopByOrderByIdDesc();
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
            return String.format("TS%04d", suffix);
        }
    }
    @Override
    public LocalDate getDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate;
    }

    }

