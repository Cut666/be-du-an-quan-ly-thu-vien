package com.ntt.spring.security.login.security.services.itp;


import com.ntt.spring.security.login.models.dto.ExportDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Rental;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExportService {
    public Warehouse getByCode(String code);
    public List<Warehouse> getAll(long id);
    public Object creatWarehouse(ExportDTO dto,String cccd);
    public Object updateWarehouse(ExportDTO dto,String cccd);
    public Object deleteWarehouseExport(int id);
    public Warehouse creatWarehouseForSell(ExportDTO dto,String cccd, Long id);
    public Warehouse updateWarehouseForRental(ExportDTO dto, String cccd, Rental rental);
    public Warehouse creatWarehouseForSellLostBook(ExportDTO dto,String cccd, Long id);
    public Warehouse updateWarehouseForSell(ExportDTO dto, String cccd, BuySellBook buySellBook);
    public String generateReceiptCode(Long id);
}
