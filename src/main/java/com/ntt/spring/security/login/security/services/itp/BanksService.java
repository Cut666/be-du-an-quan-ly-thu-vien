package com.ntt.spring.security.login.security.services.itp;

import com.ntt.spring.security.login.models.entity.professionalKnowledge.Banks;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.CashFund;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BanksService {
    public List<Banks> getByCode(String code, long id);
    public List<Banks> getAll(Long id);
    public Object delete(int id);
    public Banks getById(int id);
}
