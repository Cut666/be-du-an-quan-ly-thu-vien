package com.ntt.spring.security.login.security.services;

import com.ntt.spring.security.login.models.entity.professionalKnowledge.Banks;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.BanksRepository;
import com.ntt.spring.security.login.security.services.itp.BanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BanksServiceImpl implements BanksService {
    @Autowired
    public BanksRepository banksRepository;

    @Override
    public List<Banks> getByCode(String code, long id) {
        List<Banks> banks = banksRepository.findByUserIdAndCodeContaining(id,code);
        return banks;
    }

    @Override
    public List<Banks> getAll(Long id) {
        List<Banks> banks = banksRepository.findByUserId(id);
        return banks;
    }

    @Override
    public Object delete(int id) {
        Banks banks = banksRepository.findById(id).get();
        banksRepository.delete(banks);
        return new MessageResponse("đã xóa thành công");

    }

    @Override
    public Banks getById(int id) {
        return banksRepository.findById(id).get();
    }
}
