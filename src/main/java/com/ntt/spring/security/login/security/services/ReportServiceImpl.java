package com.ntt.spring.security.login.security.services;

import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
import com.ntt.spring.security.login.models.fileenum.StatusBanks;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusCashFund;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.repository.*;
import com.ntt.spring.security.login.security.services.itp.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    public BuySellBookRepository buySellBookRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SubjectsRepository subjectsRepository;
    @Autowired
    public ReRentalRepository reRentalRepository;
    @Autowired
    public RentalRepository rentalRepository;
    @Autowired
    public CashFundRepository cashFundRepository;
    @Autowired
    public BanksRepository banksRepository;
    public List<BuySellBook> getBuyBooksByConditions(Long userid, String code,LocalDate startDate) {
        StatusBuySell statusBuySell = StatusBuySell.buy;
        StatusPay statusPay = StatusPay.noPay;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return buySellBookRepository.findByUserAndSubjectsAndStatusBuySellAndStatusPayAndCollectionDateBefore(user, subjects, statusBuySell, statusPay,startDate);
    }
    public List<BuySellBook> getSellBooksByConditions(Long userid, String code,LocalDate startDate) {
        StatusBuySell statusBuySell = StatusBuySell.sell;
        StatusPay statusPay = StatusPay.noPay;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return buySellBookRepository.findByUserAndSubjectsAndStatusBuySellAndStatusPayAndPaymentDateBefore(user, subjects, statusBuySell, statusPay,startDate);
    }
    public List<ReRental> getReRentalByConditions(Long userid, String code,LocalDate startDate) {
        StatusPay statusPay = StatusPay.noPay;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return reRentalRepository.findByUserAndSubjectsAndStatusPayAndDateAddBefore(user, subjects, statusPay, startDate);
    }
    public List<CashFund> getphieuChiByConditions(Long userid, String code,LocalDate startDate) {
        StatusCashFund statusCashFund = StatusCashFund.phieuChi;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return cashFundRepository.findByUserAndSubjectsAndStatusCashFundAndPaymentDateBefore(user, subjects,statusCashFund,startDate);
    }
    public List<CashFund> getphieuThuByConditions(Long userid, String code,LocalDate startDate) {
        StatusCashFund statusCashFund = StatusCashFund.phieuThu;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return cashFundRepository.findByUserAndSubjectsAndStatusCashFundAndCollectionDateBefore(user, subjects,statusCashFund,startDate);
    }
    public List<Banks> getBaoCoByConditions(Long userid, String code,LocalDate startDate) {
        StatusBanks statusBanks = StatusBanks.baoCo;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return banksRepository.findByUserAndSubjectsAndStatusBanksAndCollectionDateBefore(user, subjects,statusBanks,startDate);
    }
    public List<Banks> getUNCByConditions(Long userid, String code,LocalDate startDate) {
        StatusBanks statusBanks = StatusBanks.UNC;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return banksRepository.findByUserAndSubjectsAndStatusBanksAndPaymentDateBefore(user, subjects,statusBanks,startDate);
    }


    public List<BuySellBook> getBuyBooksByConditionsin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        StatusBuySell statusBuySell = StatusBuySell.buy;
        StatusPay statusPay = StatusPay.noPay;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return buySellBookRepository.findByUserAndSubjectsAndStatusBuySellAndStatusPayAndCollectionDateBetween(user, subjects, statusBuySell, statusPay,startDate,endDate);
    }
    public List<BuySellBook> getSellBooksByConditionsin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        StatusBuySell statusBuySell = StatusBuySell.sell;
        StatusPay statusPay = StatusPay.noPay;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return buySellBookRepository.findByUserAndSubjectsAndStatusBuySellAndStatusPayAndPaymentDateBetween(user, subjects, statusBuySell, statusPay,startDate,endDate);
    }
    public List<ReRental> getReRentalByConditionsin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        StatusPay statusPay = StatusPay.noPay;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return reRentalRepository.findByUserAndSubjectsAndStatusPayAndDateAddBetween(user, subjects, statusPay, startDate,endDate);
    }
    public List<CashFund> getphieuChiByConditionsin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        StatusCashFund statusCashFund = StatusCashFund.phieuChi;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return cashFundRepository.findByUserAndSubjectsAndStatusCashFundAndPaymentDateBetween(user, subjects,statusCashFund,startDate,endDate);
    }
    public List<CashFund> getphieuThuByConditionsin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        StatusCashFund statusCashFund = StatusCashFund.phieuThu;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return cashFundRepository.findByUserAndSubjectsAndStatusCashFundAndCollectionDateBetween(user, subjects,statusCashFund,startDate,endDate);
    }
    public List<Banks> getBaoCoByConditionsin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        StatusBanks statusBanks = StatusBanks.baoCo;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return banksRepository.findByUserAndSubjectsAndStatusBanksAndCollectionDateBetween(user, subjects,statusBanks,startDate,endDate);
    }
    public List<Banks> getUNCByConditionsin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        StatusBanks statusBanks = StatusBanks.UNC;
        User user = userRepository.findById(userid).get();
        Subjects subjects = subjectsRepository.findByUserIdAndCode(userid,code);
        return banksRepository.findByUserAndSubjectsAndStatusBanksAndPaymentDateBetween(user, subjects,statusBanks,startDate,endDate);
    }
    @Override
    public double getcollectbefor(Long userid, String code,LocalDate startDate) {
        List<BuySellBook> sell = getSellBooksByConditions(userid,code,startDate);
        double totalSell=0;
        for (BuySellBook buySellBook:sell){
            double money= buySellBook.getMoney();
            totalSell+=money;
        }
        List<ReRental> reRentals = getReRentalByConditions(userid,code,startDate);
        double totalReRental=0;
        for (ReRental reRental: reRentals){
            totalReRental+=reRental.getTotalprice();
        }
        List<CashFund> cashFunds= getphieuChiByConditions(userid,code,startDate);
        double totalCashFund = 0;
        for (CashFund cashFund: cashFunds){
            totalCashFund+=cashFund.getMoney();
        }
        List<Banks> banks =getUNCByConditions(userid,code,startDate);
        double totalBanks=0;
        for (Banks banks1 : banks){
            totalBanks+=banks1.getMoney();
        }
        return totalSell+totalReRental+totalCashFund+totalBanks;
    }
    @Override
    public double getspendbefor(Long userid, String code,LocalDate startDate) {
        List<BuySellBook> buy = getBuyBooksByConditions(userid,code,startDate);
        double totalBuy=0;
        for (BuySellBook buySellBook:buy){
            double money= buySellBook.getMoney();
            totalBuy+=money;
        }
        List<CashFund> cashFunds= getphieuThuByConditions(userid,code,startDate);
        double totalCashFund = 0;
        for (CashFund cashFund: cashFunds){
            totalCashFund+=cashFund.getMoney();
        }
        List<Banks> banks =getBaoCoByConditions(userid,code,startDate);
        double totalBanks=0;
        for (Banks banks1 : banks){
            totalBanks+=banks1.getMoney();
        }
        return totalBuy+totalCashFund+totalBanks;
    }
    @Override
    public double getcollectin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        List<BuySellBook> sell = getSellBooksByConditionsin(userid,code,startDate,endDate);
        double totalSell=0;
        for (BuySellBook buySellBook:sell){
            double money= buySellBook.getMoney();
            totalSell+=money;
        }
        List<ReRental> reRentals = getReRentalByConditionsin(userid,code,startDate,endDate);
        double totalReRental=0;
        for (ReRental reRental: reRentals){
            totalReRental+=reRental.getTotalprice();
        }
        List<CashFund> cashFunds= getphieuChiByConditionsin(userid,code,startDate,endDate);
        double totalCashFund = 0;
        for (CashFund cashFund: cashFunds){
            totalCashFund+=cashFund.getMoney();
        }
        List<Banks> banks =getUNCByConditionsin(userid,code,startDate,endDate);
        double totalBanks=0;
        for (Banks banks1 : banks){
            totalBanks+=banks1.getMoney();
        }
        double a =totalSell+totalReRental+totalCashFund+totalBanks;
        return a;
    }
    @Override
    public double getspendin(Long userid, String code,LocalDate startDate,LocalDate endDate) {
        List<BuySellBook> buy = getBuyBooksByConditionsin(userid,code,startDate,endDate);
        double totalBuy=0;
        for (BuySellBook buySellBook:buy){
            double money= buySellBook.getMoney();
            totalBuy+=money;
        }
        List<CashFund> cashFunds= getphieuThuByConditionsin(userid,code,startDate,endDate);
        double totalCashFund = 0;
        for (CashFund cashFund: cashFunds){
            totalCashFund+=cashFund.getMoney();
        }
        List<Banks> banks =getBaoCoByConditionsin(userid,code,startDate,endDate);
        double totalBanks=0;
        for (Banks banks1 : banks){
            totalBanks+=banks1.getMoney();
        }
        return totalBuy+totalCashFund+totalBanks;
    }
@Override
    public double getTotalMoneySell(Long userId, LocalDate endDate) {
        StatusBuySell status = StatusBuySell.sell;
        double totalMoney=0;
        List<BuySellBook> buySellBooks = buySellBookRepository.findByUserIdAndStatusBuySellAndPaymentDateBefore(userId, status, endDate);
        for (BuySellBook buySellBook: buySellBooks){
            totalMoney+=buySellBook.getMoney();
        }
        return totalMoney;
    }
    @Override
    public List<BuySellBook> getListBuySellBook(Long userId, LocalDate startDate,LocalDate endDate) {
        StatusBuySell status = StatusBuySell.sell;
        List<BuySellBook> buySellBooks = buySellBookRepository.findByUserIdAndStatusBuySellAndPaymentDateBetween(userId, status,startDate, endDate);
        return buySellBooks;
    }

    @Override
    public double getTotalMoneyReRental(Long userId, LocalDate startDate) {
        double totalMoney=0;
        List<ReRental> reRentals = reRentalRepository.findByUserIdAndDateAddBefore(userId, startDate);
        for (ReRental reRental: reRentals){
            totalMoney+=reRental.getTotalprice();
        }
        return totalMoney;
    }

    @Override
    public List<ReRental> getListReRental(Long userId, LocalDate startDate, LocalDate endDate) {
        List<ReRental> reRentals = reRentalRepository.findByUserIdAndDateAddBetween(userId, startDate, endDate);
        return reRentals;
    }
}
