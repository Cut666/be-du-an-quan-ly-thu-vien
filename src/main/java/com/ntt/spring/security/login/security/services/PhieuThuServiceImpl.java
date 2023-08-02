package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.dto.CustomerDTO;
import com.ntt.spring.security.login.models.dto.PhieuThuDTO;
import com.ntt.spring.security.login.models.dto.SupplierDTO;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import com.ntt.spring.security.login.models.fileenum.StatusCashFund;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.CashFundRepository;
import com.ntt.spring.security.login.repository.CashRepository;
import com.ntt.spring.security.login.repository.SubjectsRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.security.services.itp.CashService;
import com.ntt.spring.security.login.security.services.itp.CustomerService;
import com.ntt.spring.security.login.security.services.itp.PhieuThuService;
import com.ntt.spring.security.login.security.services.itp.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PhieuThuServiceImpl implements PhieuThuService {
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
    @Autowired
    public SubjectsRepository subjectsRepository;

    @Override
    public CashFund getByCode(String code) {
        CashFund cashFund = cashFundRepository.findByCode(code);
        if (cashFund == null) {
            return null;
        } else {
            return cashFund;
        }
    }

    @Override
    public List<CashFund> getAll() {
        List<CashFund> cashFunds = cashFundRepository.findByStatusCashFund(StatusCashFund.phieuThu);
        return cashFunds;
    }

    @Override
    public Object creatCashFund(PhieuThuDTO dto, String cccd) {
        CashFund cashFund = new CashFund();
        cashFund.setCode(dto.getCode());
        cashFund.setCollectionDate(dto.getCollectionDate());
        cashFund.setNote(dto.getNote());
        cashFund.setMoney(dto.getMoney());
        cashFund.setStatusCashFund(StatusCashFund.phieuThu);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(),cccd);
        if (subjects1==null){
            throw new RuntimeException("đối tượng chưa được tạo");
        }else {
            cashFund.setSubjects(subjects1);
            cashFund.setUser(dto.getLibrary());
            User library = libraryRepository.findById(dto.getLibrary().getId()).get();
            library.getCash().setBanlance(library.getCash().getBanlance() + dto.getMoney());
            libraryRepository.save(library);
            cashFundRepository.save(cashFund);
            return new MessageResponse("tạo thành công");
        }
    }

    @Override
    public CashFund creatCashFundForSell(PhieuThuDTO dto, String cccd) {
        CashFund cashFund = new CashFund();
        cashFund.setCode(generateReceiptCode(dto.getLibrary().getId()));
        cashFund.setCollectionDate(dto.getCollectionDate());
        cashFund.setNote(dto.getNote());
        cashFund.setMoney(dto.getMoney());
        cashFund.setStatusCashFund(StatusCashFund.phieuThu);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(),cccd);
        if (subjects1==null){
            throw new RuntimeException("đối tượng chưa được tạo");
        }else {
            cashFund.setSubjects(subjects1);
            cashFund.setUser(dto.getLibrary());
            User library = libraryRepository.findById(dto.getLibrary().getId()).get();
            library.getCash().setBanlance(library.getCash().getBanlance() + dto.getMoney());
            libraryRepository.save(library);
            cashFundRepository.save(cashFund);
            return cashFund;
        }
    }

    @Override
    public Object updateCashFund(PhieuThuDTO dto,String cccd) {
        CashFund cashFund = cashFundRepository.findById(dto.getId()).get();
        if (cashFund == null) {
            return null;
        } else {
            cashFund.setCode(cashFund.getCode());
            cashFund.setCollectionDate(dto.getCollectionDate());
            cashFund.setNote(dto.getNote());
            cashFund.setStatusCashFund(StatusCashFund.phieuThu);
            Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(),cccd);
            if (subjects1==null){
                throw new RuntimeException("đối tượng chưa được tạo");
            }else {
                cashFund.setSubjects(subjects1);
                cashFund.setUser(dto.getLibrary());
                User library = libraryRepository.findById(dto.getLibrary().getId()).get();
                library.getCash().setBanlance(library.getCash().getBanlance()-cashFund.getMoney()+ dto.getMoney());
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
        user.getCash().setBanlance(user.getCash().getBanlance()-cashFund.getMoney());
        libraryRepository.save(user);
        cashFundRepository.delete(cashFund);
        return new MessageResponse("xóa phiếu chi thành công");
    }
    @Override
    public String generateReceiptCode(Long id) {
        StatusCashFund statusCashFund = StatusCashFund.phieuThu;
        List<CashFund> cashFunds = cashFundRepository.findByUserIdAndStatusCashFund(id,statusCashFund);
        if (cashFunds.isEmpty()) {
            return "PT0001";
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
            return String.format("PT%04d", suffix);
        }
    }
}
