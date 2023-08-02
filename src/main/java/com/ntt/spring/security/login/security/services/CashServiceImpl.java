package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.entity.Cash;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.repository.CashRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.security.services.itp.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CashServiceImpl implements CashService {
    @Autowired
    CashRepository cashRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Cash getById(int id) {
        return cashRepository.findById(id).get();
    }
    @Override
    public double getmoney(Long id){
        User user = userRepository.findById(id).get();
        Cash cash = user.getCash();
        return cash.getBanlance();
    }
    @Override
    public Cash createCash() {
        Cash cash = new Cash();
        cash.setBanlance(0);
        cashRepository.save(cash);
        return cash;
    }

    @Override
    public boolean deleteCash(int id) {
        return false;
    }

    @Override
    public Cash updateCashPhieuThu(Cash cashDTO) {
        Cash cash = cashRepository.findById(cashDTO.getId()).get();
        cash.setBanlance(cash.getBanlance()+cashDTO.getBanlance());
        cashRepository.save(cash);
        return cash;
    }

    @Override
    public Cash updateCashPhieuChi(Cash cashDTO) {
        Cash cash = cashRepository.findById(cashDTO.getId()).get();
        cash.setBanlance(cash.getBanlance()-cashDTO.getBanlance());
        cashRepository.save(cash);
        return cash;
    }

}
