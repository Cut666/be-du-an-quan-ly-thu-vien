package com.ntt.spring.security.login.security.services.itp;

import com.ntt.spring.security.login.models.dto.BuyDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BuyService {
    public Object creatBuySell(BuyDTO dto,String cccd);
    public Object updateBuySell(BuyDTO dto,String cccd);
    public Object deleteBuySell(int id,Long userid);
    public String generateReceiptCode(Long id);
}
