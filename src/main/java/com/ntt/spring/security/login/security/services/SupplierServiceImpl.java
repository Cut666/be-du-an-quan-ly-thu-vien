package com.ntt.spring.security.login.security.services;

import com.ntt.spring.security.login.models.dto.SupplierDTO;
import com.ntt.spring.security.login.models.entity.Subjectenum;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.Sub;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.SubjectenumRepository;
import com.ntt.spring.security.login.repository.SubjectsRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.security.services.itp.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ntt.spring.security.login.models.fileenum.Sub.customer;
import static com.ntt.spring.security.login.models.fileenum.Sub.supplier;


@Component
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    SubjectsRepository subjectsRepository;
    @Autowired
    SubjectenumRepository subjectenumRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Subjects> getByTaxCode(Sub sub,Long id, String taxcode) {
        List<Subjects> subjects = subjectsRepository.findSubjectsBySubjectEnumAndUserAndTaxcode(sub,id,taxcode);
        if (subjects == null) {
            return null;
        } else {
            return subjects;
        }
    }

    @Override
    public List<Subjects> getAll(Sub sub, Long id) {
        User user1 = userRepository.findById(id).get();
        if (user1==null) {throw new RuntimeException("Cần đăng nhập tài khoản");}
        else {
            Subjectenum subjectenum = subjectenumRepository.findBySub(sub);
            List<Subjects> subjects = subjectsRepository.findSubjectsBySubjectEnumAndUser(subjectenum.getSub(),id);
            return subjects;
        }
    }
    @Override
    public List<Subjects> getIn(Long id) {
        User user1 = userRepository.findById(id).get();
        if (user1==null) {throw new RuntimeException("Cần đăng nhập tài khoản");}
        else {
            List<Subjects> subjects = subjectsRepository.findSubjectsByUser(user1);
            return subjects;
        }
    }

    @Override
    public Object creatSupplier(SupplierDTO dto) {
        Subjects subjects2 = subjectsRepository.findSubjectsByTaxcodeAndUser(dto.getTaxcode(),dto.getUser());
        Subjects subjects = subjectsRepository.findSubjectsByPhoneAndUser(dto.getPhone(),dto.getUser());
        if (subjects2 != null) {
            return new MessageResponse("Mã số thuế đã được đăng ký");
        }else if (subjects !=null){
            return new MessageResponse("Số điện thoại đã được đăng ký");
        } else {
            Subjects subjects1 = new Subjects();
            subjects1.setName(dto.getName());
            subjects1.setPhone(dto.getPhone());
            subjects1.setGmail(dto.getGmail());
            subjects1.setTaxcode(dto.getTaxcode());
            subjects1.setCCCD(dto.getCCCD());
            subjects1.setAddress(dto.getAddress());

            User user = userRepository.findById(dto.getUser().getId()).get();
            subjects1.setUser(user);
            Subjectenum subjectenum1 = subjectenumRepository.findBySub(supplier);
            if (subjectenum1==null){
                Subjectenum subjectenum = new Subjectenum();
                subjectenum.setSub(supplier);
                subjectenumRepository.save(subjectenum);
                subjects1.setSubjectenums(Arrays.asList(subjectenum));
            }else {
                subjects1.setSubjectenums(Arrays.asList(subjectenum1));
            }
            subjectsRepository.save(subjects1);
            return new MessageResponse("tạo thành công");
        }

    }

    @Override
    public Subjects updateWalletSupplier(SupplierDTO dto) {
        Subjects subjects = subjectsRepository.findById(dto.getId()).get();
//        subjects.getWallet().setBalance(dto.getWallet().getBalance());
        subjectsRepository.save(subjects);
        return subjects;
    }

    @Override
    public Object updateSupplier(SupplierDTO dto) {
        Subjects subjects = subjectsRepository.findById(dto.getId()).get();
        Subjects subjectsWithCCCD = subjectsRepository.findSubjectsByTaxcodeAndUser(dto.getTaxcode(), dto.getUser());
        Subjects subjectsWithPhone = subjectsRepository.findSubjectsByPhoneAndUser(dto.getPhone(), dto.getUser());

        // Kiểm tra CCCD trùng
        if (subjectsWithCCCD != null && subjectsWithCCCD.getId() != subjects.getId()) {
            return new MessageResponse("Mã số thuế đã được đăng ký");
        }

        // Kiểm tra số điện thoại trùng
        if (subjectsWithPhone != null && subjectsWithPhone.getId() != subjects.getId()) {
            return new MessageResponse("Số điện thoại đã được đăng ký");
        }
        subjects.setName(dto.getName());
        subjects.setPhone(dto.getPhone());
        subjects.setGmail(dto.getGmail());
        subjects.setTaxcode(dto.getTaxcode());
        subjects.setCCCD(dto.getCCCD());
        subjects.setAddress(dto.getAddress());
        subjects.setSubjectenums(dto.getSubjectenums());
        subjectsRepository.save(subjects);
        return new MessageResponse("Cập nhật thành công");
    }

    @Override
    public Object deleteSupplier(int id) {
        Subjects subjects = subjectsRepository.findById(id).get();
        List<Subjectenum> subjectenums =subjects.getSubjectenums();
        subjectenums.removeAll(subjectenums);
        subjectsRepository.delete(subjects);
        return new MessageResponse("Xóa thành công");
    }

    @Override
    public Subjects creatSupplier1(SupplierDTO dto) {
//        ModelMapper mapper = new ModelMapper();
//        Subjects subjects = subjectsRepository.findByTaxcode(dto.getTaxcode());
//        if (subjects!= null){
//            return subjects;
//        }else {
////            WalletDTO wallet = mapper.map(dto.getWallet(), WalletDTO.class);
////            Wallet wallet1 = walletService.createWallet(wallet);
//
//            Subjects subjects1 = new Subjects();
//            subjects1.setName(dto.getName());
//            subjects1.setPhone(dto.getPhone());
//            subjects1.setGmail(dto.getPhone());
//            subjects1.setTaxcode(dto.getTaxcode());
//            subjects1.setAddress(dto.getAddress());
//
//            Subjectenum subjectenum1 = subjectenumRepository.findBySub(supplier);
//            if (subjectenum1==null){
//                Subjectenum subjectenum = new Subjectenum();
//                subjectenum.setSub(supplier);
//                subjectenumRepository.save(subjectenum);
//                List<Subjectenum> subjectenums = new ArrayList<>();
//                subjectenums.add(subjectenum);
//                subjects1.setSubjectenums(subjectenums);
//            }else {
//                List<Subjectenum> subjectenums = new ArrayList<>();
//                subjectenums.add(subjectenum1);
//                subjects1.setSubjectenums(subjectenums);
//            }
////            subjects1.setWallet(wallet1);
//            subjectsRepository.save(subjects1);
//            return subjects1;
//        }
        return null;
    }

    @Override
    public Object getByCoreOrTax(long id, String code) {
        Subjects subjects = subjectsRepository.findByUserIdAndCode(id,code);
        if (subjects==null){
            throw new RuntimeException("đối tượng chưa được tạo");
        }else {
            return subjects;
        }

    }
}
