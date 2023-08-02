package com.ntt.spring.security.login.security.services;

import com.ntt.spring.security.login.models.dto.BaoCoDTO;
import com.ntt.spring.security.login.models.dto.CustomerDTO;
import com.ntt.spring.security.login.models.dto.SupplierDTO;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Banks;
import com.ntt.spring.security.login.models.fileenum.StatusBanks;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.BanksRepository;
import com.ntt.spring.security.login.repository.SubjectsRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.security.services.itp.BaoCoService;
import com.ntt.spring.security.login.security.services.itp.CustomerService;
import com.ntt.spring.security.login.security.services.itp.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class BaoCoServiceImpl implements BaoCoService {
    @Autowired
    BanksRepository banksRepository;
    @Autowired
    public CustomerService customerService;
    @Autowired
    public SupplierService supplierService;
    @Autowired
    public UserRepository libraryRepository;
    @Autowired
    public SubjectsRepository subjectsRepository;


    @Override
    public Object createBanks(BaoCoDTO dto, String cccd) {
        Banks banks = new Banks();
        banks.setCode(dto.getCode());
        banks.setCollectionDate(dto.getCollectionDate());
        banks.setNote(dto.getNote());
        banks.setMoney(dto.getMoney());
        banks.setStatusBanks(StatusBanks.baoCo);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(),cccd);
        if (subjects1==null){
            throw new RuntimeException("đối tượng chưa được tạo");
        }else {
            banks.setSubjects(subjects1);
            banks.setUser(dto.getLibrary());
            User library = libraryRepository.findById(dto.getLibrary().getId()).get();
            library.getWallet().setBalance(library.getWallet().getBalance() + dto.getMoney());
            libraryRepository.save(library);
            banksRepository.save(banks);
            return new MessageResponse("tạo thành công");
        }
    }
    @Override
    public Banks createBanksForSell(BaoCoDTO dto, String cccd) {
        Banks banks = new Banks();
        banks.setCode(generateReceiptCode(dto.getLibrary().getId()));
        banks.setCollectionDate(dto.getCollectionDate());
        banks.setNote(dto.getNote());
        banks.setMoney(dto.getMoney());
        banks.setStatusBanks(StatusBanks.baoCo);
        Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(),cccd);
        if (subjects1==null){
            throw new RuntimeException("đối tượng chưa được tạo");
        }else {
            banks.setSubjects(subjects1);
            banks.setUser(dto.getLibrary());
            User library = libraryRepository.findById(dto.getLibrary().getId()).get();
            library.getWallet().setBalance(library.getWallet().getBalance() + dto.getMoney());
            libraryRepository.save(library);
            banksRepository.save(banks);
            return banks;
        }
    }

    @Override
    public Object updateBanks(BaoCoDTO dto,String cccd) {
        Banks banks = banksRepository.findById(dto.getId()).get();
        if (banks == null) {
            return null;
        } else {
            banks.setCode(banks.getCode());
            banks.setCollectionDate(dto.getCollectionDate());
            banks.setNote(dto.getNote());
            banks.setStatusBanks(StatusBanks.baoCo);
            Subjects subjects1 = subjectsRepository.findByUserIdAndCode(dto.getLibrary().getId(), cccd);
            if (subjects1 == null) {
                throw new RuntimeException("đối tượng chưa được tạo");
            } else {
                banks.setSubjects(subjects1);
                banks.setUser(dto.getLibrary());
                User library = libraryRepository.findById(dto.getLibrary().getId()).get();
                library.getWallet().setBalance(library.getWallet().getBalance()-banks.getMoney() + dto.getMoney());
                libraryRepository.save(library);

                banks.setMoney(dto.getMoney());
                banksRepository.save(banks);
                return new MessageResponse("cập nhật thành công");
            }

        }
    }

    @Override
    public Object deleteBanks(int id, Long userid) {
        Banks banks = banksRepository.findById(id).get();
        User user = libraryRepository.findById(userid).get();
        user.getWallet().setBalance(user.getWallet().getBalance()-banks.getMoney());
        libraryRepository.save(user);
        banksRepository.delete(banks);
        return new MessageResponse("xóa UNC thành công");
    }

    @Override
    public String generateReceiptCode(Long id) {
        StatusBanks statusBanks = StatusBanks.baoCo;
        List<Banks> banks = banksRepository.findByUserIdAndStatusBanks(id,statusBanks);
        if (banks.isEmpty()){
            return "BC0001";
        }else {
            Banks bank = banks.stream()
                    .max(Comparator.comparingInt(Banks::getId))
                    .orElse(null);
            String code = bank.getCode();
            int suffix = Integer.parseInt(code.substring(2));
            if (suffix > 9999) {
                suffix = 1;
//                prefix++;
            } else {
                suffix++;
            }
            return String.format("BC%04d", suffix);
        }
    }
}
