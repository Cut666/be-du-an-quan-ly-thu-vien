package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.dto.ReceiptDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReceiptService {
    public Object creatWarehouseReceipt(ReceiptDTO dto,String cccd);
    public Object updateWarehouseReceipt(ReceiptDTO dto,String cccd);
    public Object deleteWarehouseReceipt(int id);
    public Warehouse creatWarehouseReceiptforBuy(ReceiptDTO dto,String cccd,Long id);
    public Warehouse updateWarehouseReceiptForbuy(ReceiptDTO dto, String cccd, BuySellBook buySellBook);
    public String generateReceiptCode(Long id);
}
