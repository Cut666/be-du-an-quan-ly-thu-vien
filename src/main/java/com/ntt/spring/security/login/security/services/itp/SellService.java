package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.dto.SellDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellService {
    public Object creatSellLostBook(SellDTO dto,String cccd);
    public Object creatBuySell(SellDTO dto,String cccd);
    public Object updateBuySell(SellDTO dto,String cccd);
    public Object deleteBuySell(int id,Long userid);
    public String generateReceiptCode(Long id);
}
