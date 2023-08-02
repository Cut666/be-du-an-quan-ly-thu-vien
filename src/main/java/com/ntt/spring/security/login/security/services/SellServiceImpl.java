package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.dto.SellDTO;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.*;
import com.ntt.spring.security.login.security.services.itp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Component
@Transactional
public class SellServiceImpl implements SellService {
    @Autowired
    BuySellBookRepository buySellBookRepository;
    @Autowired
    ProductService productService;
    @Autowired
    WalletService walletService;
    @Autowired
    CashService cashService;
    @Autowired
    ExportService exportService;
    @Autowired
    PhieuThuService phieuThuService;
    @Autowired
    CashFundRepository cashFundRepository;
    @Autowired
    BanksRepository banksRepository;
    @Autowired
    UserRepository libraryRepository;
    @Autowired
    BaoCoService baoCoService;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    WarehouseProductRepository warehouseProductRepository;
@Autowired
SubjectsRepository subjectsRepository;



    @Override
    public Object creatBuySell(SellDTO dto,String cccd) {
        BuySellBook buySellBook = new BuySellBook();
        buySellBook.setCode(dto.getCode());
        buySellBook.setPaymentDate(dto.getPaymentDate());
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        buySellBook.setSubjects(subjects1);
        Warehouse warehouse=exportService.creatWarehouseForSell(dto.getExportDTO(),cccd,dto.getUser().getId());
        warehouse.setExportdate(dto.getPaymentDate());
        warehouse.setNote(dto.getNote());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        double money = total(warehouseProducts);
        buySellBook.setMoney(money);
        buySellBook.setNote(dto.getNote());
        buySellBook.setStatusBuySell(StatusBuySell.sell);
        buySellBook.setWarehouse(warehouse);
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        buySellBook.setUser(user);
        buySellBook.setStatusPay(dto.getStatusPay());
        if (dto.getStatusPay() == StatusPay.noPay) {
            buySellBookRepository.save(buySellBook);
            return new MessageResponse("tạo phiếu bán hàng thành công");
        } else {
            if (dto.getPhieuThuDTO()!=null){
                buySellBookRepository.save(buySellBook);
                CashFund cashFund = phieuThuService.creatCashFundForSell(dto.getPhieuThuDTO(),cccd);
                cashFund.setPaymentDate(dto.getPaymentDate());
                cashFund.setNote(dto.getNote());
                cashFund.setMoney(money);
                cashFund.setBuySellBook(buySellBook);
                cashFundRepository.save(cashFund);
                user.getCash().setBanlance(user.getCash().getBanlance()+money);
                libraryRepository.save(user);
            }else if (dto.getBaoCoDTO()!=null){
                buySellBookRepository.save(buySellBook);
                buySellBookRepository.save(buySellBook);
                Banks banks = baoCoService.createBanksForSell(dto.getBaoCoDTO(),cccd);
                banks.setPaymentDate(dto.getPaymentDate());
                banks.setNote(dto.getNote());
                banks.setMoney(money);
                banks.setBuySellBook(buySellBook);
                banksRepository.save(banks);
                user.getWallet().setBalance(user.getWallet().getBalance()+money);
                libraryRepository.save(user);
            }
            return new MessageResponse("tạo phiếu bán hàng thành công");
        }
    }
    @Override
    public Object creatSellLostBook(SellDTO dto,String cccd){
        BuySellBook buySellBook = new BuySellBook();
        buySellBook.setCode(dto.getCode());
        buySellBook.setPaymentDate(dto.getPaymentDate());
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        buySellBook.setSubjects(subjects1);
        Warehouse warehouse=exportService.creatWarehouseForSellLostBook(dto.getExportDTO(),cccd,dto.getUser().getId());
        warehouse.setExportdate(dto.getPaymentDate());
        warehouse.setNote(dto.getNote());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        double money = total(warehouseProducts);
        buySellBook.setMoney(money);
        buySellBook.setNote(dto.getNote());
        buySellBook.setStatusBuySell(StatusBuySell.sell);
        buySellBook.setWarehouse(warehouse);
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        buySellBook.setUser(user);
        buySellBook.setStatusPay(StatusPay.noPay);
        buySellBookRepository.save(buySellBook);
        return new MessageResponse("tạo phiếu bán hàng thành công");
    }
    @Override
    public Object updateBuySell(SellDTO dto,String cccd) {
        BuySellBook buySellBook = buySellBookRepository.findById(dto.getId()).get();
        buySellBook.setCode(dto.getCode());
        buySellBook.setPaymentDate(dto.getPaymentDate());
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        buySellBook.setSubjects(subjects1);
        Warehouse warehouse=exportService.updateWarehouseForSell(dto.getExportDTO(),cccd,buySellBook);
        warehouse.setExportdate(dto.getPaymentDate());
        warehouse.setNote(dto.getNote());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        double money = total(warehouseProducts);
        buySellBook.setMoney(money);
        buySellBook.setNote(dto.getNote());
        buySellBook.setStatusBuySell(StatusBuySell.sell);
        buySellBook.setWarehouse(warehouse);
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        buySellBook.setUser(user);
        buySellBook.setStatusPay(dto.getStatusPay());
        if (dto.getStatusPay() == StatusPay.noPay) {
            CashFund cashFund = cashFundRepository.findByBuySellBook(buySellBook);
            Banks banks = banksRepository.findByBuySellBook(buySellBook);
            if(cashFund!=null){
                user.getCash().setBanlance(user.getCash().getBanlance()-cashFund.getMoney());
                libraryRepository.save(user);
                cashFundRepository.delete(cashFund);
            }else if(banks!=null){
                user.getWallet().setBalance(user.getWallet().getBalance()-banks.getMoney());
                libraryRepository.save(user);
                banksRepository.delete(banks);
            }
            buySellBookRepository.save(buySellBook);
            return new MessageResponse("cập nhật thành công");
        } else {
            CashFund cashFund1 = cashFundRepository.findByBuySellBook(buySellBook);
            Banks banks1 =  banksRepository.findByBuySellBook(buySellBook);
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
                buySellBookRepository.save(buySellBook);
                CashFund cashFund = phieuThuService.creatCashFundForSell(dto.getPhieuThuDTO(),cccd);
                cashFund.setPaymentDate(dto.getPaymentDate());
                cashFund.setNote(dto.getNote());
                cashFund.setMoney(money);
                cashFund.setBuySellBook(buySellBook);
                cashFundRepository.save(cashFund);
                user.getCash().setBanlance(user.getCash().getBanlance()+money);
                libraryRepository.save(user);
            }else if (dto.getBaoCoDTO()!=null){
                buySellBookRepository.save(buySellBook);
                Banks banks = baoCoService.createBanksForSell(dto.getBaoCoDTO(),cccd);
                banks.setPaymentDate(dto.getPaymentDate());
                banks.setNote(dto.getNote());
                banks.setMoney(money);
                banks.setBuySellBook(buySellBook);
                banksRepository.save(banks);
                user.getWallet().setBalance(user.getWallet().getBalance()+money);
                libraryRepository.save(user);
            }
            return new MessageResponse("cập nhật thành công");
        }
    }

    @Override
    public Object deleteBuySell(int id,Long userid) {
        BuySellBook buySellBook = buySellBookRepository.findById(id).get();

        CashFund cashFund = cashFundRepository.findByBuySellBook(buySellBook);
        Banks banks = banksRepository.findByBuySellBook(buySellBook);
        if (cashFund!=null){
            phieuThuService.deleteCashFund(cashFund.getId(),userid);
        }
        if (banks!=null){
            baoCoService.deleteBanks(banks.getId(),userid);
        }
        exportService.deleteWarehouseExport(buySellBook.getWarehouse().getId());
        buySellBookRepository.delete(buySellBook);
        return new MessageResponse("xóa phiếu bán thành công");
    }
    public double total(List<WarehouseProduct> warehouseProducts) {
        double total = 0;
        for (WarehouseProduct p : warehouseProducts) {
            total += p.getTotal();
        }
        return total;
    }
    @Override
    public String generateReceiptCode(Long id) {
        StatusBuySell statusBuySell = StatusBuySell.sell;
        List<BuySellBook> buySellBooks = buySellBookRepository.findByUserIdAndStatusBuySell(id,statusBuySell);
        if (buySellBooks.isEmpty()) {
            return "BH0001";
        } else {
            BuySellBook buySellBook = buySellBooks.stream()
                    .max(Comparator.comparingInt(BuySellBook::getId))
                    .orElse(null);
            String code = buySellBook.getCode();
//            char prefix = code.charAt(0);
//            char prefix1 = code.charAt(1);
            int suffix = Integer.parseInt(code.substring(2));
            if (suffix > 9999) {
                suffix = 1;
//                prefix++;
            } else {
                suffix++;
            }
            return String.format("BH%04d", suffix);
        }
    }
}
