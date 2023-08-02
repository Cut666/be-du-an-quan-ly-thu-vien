package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.dto.BaoCoDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Banks;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaoCoService {

    public Object createBanks(BaoCoDTO dto, String cccd);
    public String generateReceiptCode(Long id);
    public Banks createBanksForSell(BaoCoDTO dto, String cccd);
    public Object updateBanks(BaoCoDTO dto, String cccd);
    public Object deleteBanks(int id, Long userid);
}
