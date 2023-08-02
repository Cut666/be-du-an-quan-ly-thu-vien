package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.dto.CustomerDTO;
import com.ntt.spring.security.login.models.dto.PhieuChiDTO;
import com.ntt.spring.security.login.models.dto.SupplierDTO;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Banks;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import com.ntt.spring.security.login.models.fileenum.StatusBanks;
import com.ntt.spring.security.login.models.fileenum.StatusCashFund;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.CashFundRepository;
import com.ntt.spring.security.login.repository.CashRepository;
import com.ntt.spring.security.login.repository.SubjectsRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.security.services.itp.CashService;
import com.ntt.spring.security.login.security.services.itp.CustomerService;
import com.ntt.spring.security.login.security.services.itp.PhieuChiService;
import com.ntt.spring.security.login.security.services.itp.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PhieuChiServiceImpl implements PhieuChiService {
    @Autowired
    SubjectsRepository subjectsRepository;
    @Autowired
    public CashFundRepository cashFundRepository;
    @Autowired
    public CustomerService customerService;
    @Autowired
    public SupplierService supplierService;
    @Autowired
    public CashRepository cashRepository;
    @Autowired
    public CashService cashService;
    @Autowired
    public UserRepository libraryRepository;


    @Override
    public Object creatCashFund(PhieuChiDTO dto, String cccd) {
        CashFund cashFund = new CashFund();
        cashFund.setCode(dto.getCode());
        cashFund.setPaymentDate(dto.getPaymentDate());
        cashFund.setNote(dto.getNote());
        cashFund.setMoney(dto.getMoney());
        cashFund.setStatusCashFund(StatusCashFund.phieuChi);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(),cccd);
        if (subjects1==null){
            throw new RuntimeException("đối tượng chưa được tạo");
        }else {
            cashFund.setSubjects(subjects1);
            cashFund.setUser(dto.getLibrary());
            User library = libraryRepository.findById(dto.getLibrary().getId()).get();
            library.getCash().setBanlance(library.getCash().getBanlance() - dto.getMoney());
            libraryRepository.save(library);
            cashFundRepository.save(cashFund);
            return new MessageResponse("tạo thành công");
        }
    }
    @Override
    public CashFund creatCashFundForBuy(PhieuChiDTO dto, String cccd) {
        CashFund cashFund = new CashFund();
        cashFund.setCode(generateReceiptCode(dto.getLibrary().getId()));
        cashFund.setPaymentDate(dto.getPaymentDate());
        cashFund.setNote(dto.getNote());
        cashFund.setMoney(dto.getMoney());
        cashFund.setStatusCashFund(StatusCashFund.phieuChi);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(),cccd);
        if (subjects1==null){
            throw new RuntimeException("đối tượng chưa được tạo");
        }else {
            cashFund.setSubjects(subjects1);
            cashFund.setUser(dto.getLibrary());
            User library = libraryRepository.findById(dto.getLibrary().getId()).get();
            library.getCash().setBanlance(library.getCash().getBanlance() - dto.getMoney());
            libraryRepository.save(library);
            cashFundRepository.save(cashFund);
            return cashFund;
        }
    }

    @Override
    public Object updateCashFund(PhieuChiDTO dto, String cccd) {
        CashFund cashFund = cashFundRepository.findById(dto.getId()).get();
        if (cashFund == null) {
            return null;
        } else {
            cashFund.setCode(cashFund.getCode());
            cashFund.setPaymentDate(dto.getPaymentDate());
            cashFund.setNote(dto.getNote());
            cashFund.setStatusCashFund(StatusCashFund.phieuChi);
            Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(),cccd);
            if (subjects1==null){
                throw new RuntimeException("đối tượng chưa được tạo");
            }else {
                cashFund.setSubjects(subjects1);
            cashFund.setUser(dto.getLibrary());
                User library = libraryRepository.findById(dto.getLibrary().getId()).get();
                library.getCash().setBanlance(library.getCash().getBanlance()+cashFund.getMoney()- dto.getMoney());
                libraryRepository.save(library);
                cashFund.setMoney(dto.getMoney());
            cashFundRepository.save(cashFund);
            return new MessageResponse("cập nhật thành công");}
        }
    }

    @Override
    public Object deleteCashFund(int id,Long userid) {
        CashFund cashFund = cashFundRepository.findById(id).get();
        User user = libraryRepository.findById(userid).get();
        user.getCash().setBanlance(user.getCash().getBanlance()+cashFund.getMoney());
        libraryRepository.save(user);
        cashFundRepository.delete(cashFund);
        return new MessageResponse("xóa phiếu chi thành công");
    }
    @Override
    public String generateReceiptCode(Long id) {
        StatusCashFund statusCashFund = StatusCashFund.phieuChi;
        List<CashFund> cashFunds = cashFundRepository.findByUserIdAndStatusCashFund(id,statusCashFund);
        if (cashFunds.isEmpty()) {
            return "PC0001";
        } else {
            CashFund cashFund = cashFunds.stream()
                    .max(Comparator.comparingInt(CashFund::getId))
                    .orElse(null);
            String code = cashFund.getCode();
            int suffix = Integer.parseInt(code.substring(2));
            if (suffix > 9999) {
                suffix = 1;
//                prefix++;
            } else {
                suffix++;
            }
            return String.format("PC%04d", suffix);
        }
    }


}

