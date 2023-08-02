package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.dto.PhieuChiDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhieuChiService {
//    public CashFund getByCode(String code);
//    public List<CashFund> getAll();
    public Object creatCashFund(PhieuChiDTO dto,String cccd);
    public CashFund creatCashFundForBuy(PhieuChiDTO dto, String cccd);
    public Object updateCashFund(PhieuChiDTO dto, String cccd);
    public Object deleteCashFund(int id,Long userid);
//    public CashFund updateCashFundForBuy(PhieuChiDTO dto);
public String generateReceiptCode(Long id);
}
