package com.ntt.spring.security.login.security.services.itp;

import com.ntt.spring.security.login.models.dto.ReRentalDTO;
import com.ntt.spring.security.login.models.dto.RentalDTO;
import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ReRentalService {
    public Object getByCode(Long id,String cccd);
    public Object getById(int id);
    public Object getAll(Long id);
    public Object creatReRental(ReRentalDTO dto, String cccd);
    public Object updateReRental(ReRentalDTO dto, String cccd);
    public Object updateStatusReRental(String code,Long userid);
    public Object deleteReRental(int id);
    public Long numberdate(LocalDate a, LocalDate b);
    public String generateReceiptCode(Long id);
    public Object updateRate(Long id, double d);
    public Object getRate(Long id);
    public Object updateMaxDay(Long id, Long d);
    public Object getMaxDay(Long id);
    public LocalDate getDate();
    public List<Product> lostBook(String code, Long userid);
}
