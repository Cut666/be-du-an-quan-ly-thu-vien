package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.dto.PhieuThuDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhieuThuService {
    public CashFund getByCode(String code);
    public List<CashFund> getAll();
    public Object creatCashFund(PhieuThuDTO dto, String cccd);
    public CashFund creatCashFundForSell(PhieuThuDTO dto, String cccd);
    public Object updateCashFund(PhieuThuDTO dto,String cccd);
    public Object deleteCashFund(int id,Long userid);
    public String generateReceiptCode(Long id);
}
