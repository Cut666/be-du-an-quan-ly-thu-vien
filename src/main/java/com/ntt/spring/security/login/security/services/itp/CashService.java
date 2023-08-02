package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.entity.Cash;
import org.springframework.stereotype.Service;

@Service
public interface CashService {
    public Cash getById(int id);
    public Cash createCash();
    public boolean deleteCash(int id);
    public Cash updateCashPhieuThu(Cash cashDTO);
    public Cash updateCashPhieuChi(Cash cashDTO);
    public double getmoney(Long id);
}
