package com.ntt.spring.security.login.security.services;

import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import com.ntt.spring.security.login.models.fileenum.StatusCashFund;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.CashFundRepository;
import com.ntt.spring.security.login.security.services.itp.CashFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CashFundServiceImpl implements CashFundService {
    @Autowired
    public CashFundRepository cashFundRepository;
    @Override
    public List<CashFund> getByCode(String code, long id) {
        List<CashFund> cashFund = cashFundRepository.findByUserIdAndCodeContaining(id,code);
            return cashFund;
    }

    @Override
    public List<CashFund> getAll(Long id) {
        List<CashFund> cashFunds = cashFundRepository.findByUserId(id);
        return cashFunds;
    }

    @Override
    public Object delete(int id) {
        CashFund cashFund = cashFundRepository.findById(id).get();
        cashFundRepository.delete(cashFund);
        return new MessageResponse("đã xóa thành công");
    }

    @Override
    public CashFund getById(int code) {
        return cashFundRepository.findById(code).get();
    }

}
