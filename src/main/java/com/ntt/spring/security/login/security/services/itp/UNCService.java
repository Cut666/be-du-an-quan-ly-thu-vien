package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.dto.UNCDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Banks;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UNCService {
    public Object createBanks(UNCDTO dto,String cccd);
    public String generateReceiptCode(Long id);
    public Banks createBanksForBuy(UNCDTO dto,String cccd);
    public Object updateBanks(UNCDTO dto,String cccd);
    public Object deleteBanks(int id, Long userid);
}
