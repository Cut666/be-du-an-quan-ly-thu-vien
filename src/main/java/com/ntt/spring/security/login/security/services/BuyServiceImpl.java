package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.dto.BuyDTO;
import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.*;
import com.ntt.spring.security.login.security.services.itp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class BuyServiceImpl implements BuyService {
    @Autowired
    BuySellBookRepository buySellBookRepository;
    @Autowired
    ProductService productService;
    @Autowired
    CashService cashService;
    @Autowired
    WalletService walletService;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    PhieuChiService phieuChiService;
    @Autowired
    CashFundRepository cashFundRepository;
    @Autowired
    BanksRepository banksRepository;
    @Autowired
    UserRepository libraryRepository;
    @Autowired
    @Qualifier("UNCServiceImpl")
    UNCService uncService;
    @Autowired
    WarehouseProductRepository warehouseProductRepository;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    SubjectsRepository subjectsRepository;

    @Override
    public Object creatBuySell(BuyDTO dto,String cccd) {
        BuySellBook buySellBook = new BuySellBook();
        buySellBook.setCode(dto.getCode());
        buySellBook.setCollectionDate(dto.getCollectionDate());
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        buySellBook.setSubjects(subjects1);
        Warehouse warehouse=receiptService.creatWarehouseReceiptforBuy(dto.getReceiptDTO(),cccd,dto.getUser().getId());
        warehouse.setDateadd(dto.getCollectionDate());
        warehouse.setNote(dto.getNote());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        double money = total(warehouseProducts);
        buySellBook.setMoney(money);
        buySellBook.setNote(dto.getNote());
        buySellBook.setStatusBuySell(StatusBuySell.buy);
        buySellBook.setWarehouse(warehouse);
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        buySellBook.setUser(user);
        buySellBook.setStatusPay(dto.getStatusPay());
        if (dto.getStatusPay() == StatusPay.noPay) {
            buySellBookRepository.save(buySellBook);
            return new MessageResponse("tạo phiếu mua hàng thành công");
        } else {
            if (dto.getPhieuChiDTO()!=null){
                buySellBookRepository.save(buySellBook);
                CashFund cashFund = phieuChiService.creatCashFundForBuy(dto.getPhieuChiDTO(),cccd);
                cashFund.setPaymentDate(dto.getCollectionDate());
                cashFund.setNote(dto.getNote());
                cashFund.setMoney(money);
                cashFund.setBuySellBook(buySellBook);
                cashFundRepository.save(cashFund);
                user.getCash().setBanlance(user.getCash().getBanlance()-money);
                libraryRepository.save(user);
            }else if (dto.getUncdto()!=null){
                buySellBookRepository.save(buySellBook);
                buySellBookRepository.save(buySellBook);
                Banks banks = uncService.createBanksForBuy(dto.getUncdto(),cccd);
                banks.setPaymentDate(dto.getCollectionDate());
                banks.setNote(dto.getNote());
                banks.setMoney(money);
                banks.setBuySellBook(buySellBook);
                banksRepository.save(banks);
                user.getWallet().setBalance(user.getWallet().getBalance()-money);
                libraryRepository.save(user);
            }
            return new MessageResponse("tạo phiếu mua hàng thành công");
        }
    }

    @Override
    public Object updateBuySell(BuyDTO dto,String cccd) {
        BuySellBook buySellBook = buySellBookRepository.findById(dto.getId()).get();
        buySellBook.setCode(dto.getCode());
        buySellBook.setCollectionDate(dto.getCollectionDate());
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getUser().getId(), cccd);
        buySellBook.setSubjects(subjects1);
        Warehouse warehouse=receiptService.updateWarehouseReceiptForbuy(dto.getReceiptDTO(),cccd,buySellBook);
        warehouse.setDateadd(dto.getCollectionDate());
        warehouse.setNote(dto.getNote());
        warehouseRepository.save(warehouse);
        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByWarehouse(warehouse);
        double money = total(warehouseProducts);
        buySellBook.setMoney(money);
        buySellBook.setNote(dto.getNote());
        buySellBook.setStatusBuySell(StatusBuySell.buy);
        buySellBook.setWarehouse(warehouse);
        User user = libraryRepository.findById(dto.getUser().getId()).get();
        buySellBook.setUser(user);
        buySellBook.setStatusPay(dto.getStatusPay());
        if (dto.getStatusPay() == StatusPay.noPay) {
            CashFund cashFund = cashFundRepository.findByBuySellBook(buySellBook);
            Banks banks = banksRepository.findByBuySellBook(buySellBook);
            if(cashFund!=null){
                user.getCash().setBanlance(user.getCash().getBanlance()+cashFund.getMoney());
                libraryRepository.save(user);
                cashFundRepository.delete(cashFund);
            }else if(banks!=null){
                user.getWallet().setBalance(user.getWallet().getBalance()+banks.getMoney());
                libraryRepository.save(user);
                banksRepository.delete(banks);
            }
            buySellBookRepository.save(buySellBook);
            return new MessageResponse("cập nhật thành công");
        } else {
            CashFund cashFund1 = cashFundRepository.findByBuySellBook(buySellBook);
            Banks banks1 =  banksRepository.findByBuySellBook(buySellBook);
            if(cashFund1!=null){
                user.getCash().setBanlance(user.getCash().getBanlance()+cashFund1.getMoney());
                libraryRepository.save(user);
                cashFundRepository.delete(cashFund1);
            }else if(banks1!=null){
                user.getWallet().setBalance(user.getWallet().getBalance()+banks1.getMoney());
                libraryRepository.save(user);
                banksRepository.delete(banks1);
            }
            if (dto.getPhieuChiDTO()!=null){
                buySellBookRepository.save(buySellBook);
                CashFund cashFund = phieuChiService.creatCashFundForBuy(dto.getPhieuChiDTO(),cccd);
                cashFund.setPaymentDate(dto.getCollectionDate());
                cashFund.setNote(dto.getNote());
                cashFund.setMoney(money);
                cashFund.setBuySellBook(buySellBook);
                cashFundRepository.save(cashFund);
                user.getCash().setBanlance(user.getCash().getBanlance()-money);
                libraryRepository.save(user);
            }else if (dto.getUncdto()!=null){
                buySellBookRepository.save(buySellBook);
                Banks banks = uncService.createBanksForBuy(dto.getUncdto(),cccd);
                banks.setPaymentDate(dto.getCollectionDate());
                banks.setNote(dto.getNote());
                banks.setMoney(money);
                banks.setBuySellBook(buySellBook);
                banksRepository.save(banks);
                user.getWallet().setBalance(user.getWallet().getBalance()-money);
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
            phieuChiService.deleteCashFund(cashFund.getId(),userid);
        }
        if (banks!=null){
            uncService.deleteBanks(banks.getId(),userid);
        }
        receiptService.deleteWarehouseReceipt(buySellBook.getWarehouse().getId());
        buySellBookRepository.delete(buySellBook);
        return new MessageResponse("xóa phiếu mua thành công");
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
        StatusBuySell statusBuySell = StatusBuySell.buy;
        List<BuySellBook> buySellBooks = buySellBookRepository.findByUserIdAndStatusBuySell(id,statusBuySell);
        if (buySellBooks.isEmpty()) {
            return "MH0001";
        } else {
            BuySellBook buySellBook = buySellBooks.stream()
                    .max(Comparator.comparingInt(BuySellBook::getId))
                    .orElse(null);
            String code = buySellBook.getCode();
            int suffix = Integer.parseInt(code.substring(2));
            if (suffix > 9999) {
                suffix = 1;
            } else {
                suffix++;
            }
            return String.format("MH%04d", suffix);
        }
    }
}
