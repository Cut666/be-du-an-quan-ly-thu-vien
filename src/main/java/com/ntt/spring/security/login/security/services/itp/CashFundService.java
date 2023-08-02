package com.ntt.spring.security.login.security.services.itp;

import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CashFundService {
    public List<CashFund> getByCode(String code, long id);
    public List<CashFund> getAll(Long id);
public Object delete(int id);
    public CashFund getById(int id);

}
