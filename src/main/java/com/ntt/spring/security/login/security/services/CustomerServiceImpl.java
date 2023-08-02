package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.dto.CustomerDTO;
import com.ntt.spring.security.login.models.entity.Subjectenum;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.ReRental;
import com.ntt.spring.security.login.models.fileenum.Sub;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.SubjectenumRepository;
import com.ntt.spring.security.login.repository.SubjectsRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.security.services.itp.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

import static com.ntt.spring.security.login.models.fileenum.Sub.customer;


@Component
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private SubjectsRepository subjectsRepository;
    @Autowired
    private SubjectenumRepository subjectenumRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Subjects> getByPhone(String cccd,Long id,Sub sub) {
        List<Subjects> subjects = subjectsRepository.findSubjectsBySubjectEnumAndUserAndCCCD(sub,id,cccd);
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
            Collections.sort(subjects, Comparator.comparingLong(Subjects::getId).reversed());
            return subjects;
        }
    }

    @Override
    public Object creatCustomer(CustomerDTO dto) {
        Subjects subjects1 = subjectsRepository.findSubjectsByCCCDAndUser(dto.getCCCD(),dto.getUser());
        Subjects subjects = subjectsRepository.findSubjectsByPhoneAndUser(dto.getPhone(),dto.getUser());
        if (subjects1 != null) {
            return new MessageResponse("CCCD đã được đăng ký");
        }else if (subjects !=null){
            return new MessageResponse("Số điện thoại đã được đăng ký");
        } else {
            Subjects subjects3 = new Subjects();
            subjects3.setName(dto.getName());
            subjects3.setPhone(dto.getPhone());
            subjects3.setGmail(dto.getGmail());
            subjects3.setCCCD(dto.getCCCD());
            subjects3.setAddress(dto.getAddress());
            User user = userRepository.findById(dto.getUser().getId()).get();
            subjects3.setUser(user);
            Subjectenum subjectenum1 = subjectenumRepository.findBySub(customer);
            if (subjectenum1==null){
            Subjectenum subjectenum = new Subjectenum();
            subjectenum.setSub(customer);
            subjectenumRepository.save(subjectenum);
            subjects3.setSubjectenums(Arrays.asList(subjectenum));
            }else {
                subjects3.setSubjectenums(Arrays.asList(subjectenum1));
            }
            subjectsRepository.save(subjects3);
            return new MessageResponse("tạo thành công");
        }
    }


    @Override
    public Object updateCustomer(CustomerDTO dto) {
        Subjects subjects = subjectsRepository.findById(dto.getId()).get();
        Subjects subjectsWithCCCD = subjectsRepository.findSubjectsByCCCDAndUser(dto.getCCCD(), dto.getUser());
        Subjects subjectsWithPhone = subjectsRepository.findSubjectsByPhoneAndUser(dto.getPhone(), dto.getUser());

        // Kiểm tra CCCD trùng
        if (subjectsWithCCCD != null && subjectsWithCCCD.getId() != subjects.getId()) {
            return new MessageResponse("CCCD đã được đăng ký");
        }

        // Kiểm tra số điện thoại trùng
        if (subjectsWithPhone != null && subjectsWithPhone.getId() != subjects.getId()) {
            return new MessageResponse("Số điện thoại đã được đăng ký");
        }
            subjects.setCCCD(dto.getCCCD());
            subjects.setName(dto.getName());
            subjects.setPhone(dto.getPhone());
            subjects.setGmail(dto.getGmail());
            subjects.setAddress(dto.getAddress());
            subjects.setSubjectenums(dto.getSubjectenums());
            subjectsRepository.save(subjects);
            return new MessageResponse("Cập nhật thành công");
    }

    @Override
    public Object deleteCustomer(int id) {
        Subjects subjects = subjectsRepository.findById(id).get();
        List<Subjectenum> subjectenums =subjects.getSubjectenums();
        subjectenums.removeAll(subjectenums);
        subjectsRepository.delete(subjects);
        return new MessageResponse("Xóa thành công");
    }

    @Override
    public Subjects creatCustomer1(CustomerDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Subjects subjects = subjectsRepository.findByPhone(dto.getPhone());
        Subjects subjects1 = subjectsRepository.findByCCCD(dto.getCCCD());
        Subjects subjects2 = subjectsRepository.findByGmail(dto.getGmail());
        if (subjects != null || subjects1 != null || subjects2 != null) {
            return subjects1;
        } else {
//            WalletDTO wallet = mapper.map(dto.getWallet(), WalletDTO.class);
//            Wallet wallet1 = walletService.createWallet(wallet);
            Subjects subjects3 = new Subjects();
            subjects3.setName(dto.getName());
            subjects3.setPhone(dto.getPhone());
            subjects3.setGmail(dto.getGmail());
            subjects3.setCCCD(dto.getCCCD());
            subjects3.setAddress(dto.getAddress());
            Subjectenum subjectenum1 = subjectenumRepository.findBySub(customer);
            if (subjectenum1==null){
                Subjectenum subjectenum = new Subjectenum();
                subjectenum.setSub(customer);
                subjectenumRepository.save(subjectenum);
                subjects3.setSubjectenums(Arrays.asList(subjectenum));
            }else {
                subjects3.setSubjectenums(Arrays.asList(subjectenum1));
            }
//            subjects3.setWallet(wallet1);
            subjectsRepository.save(subjects3);
            return subjects3;
        }
    }
}
