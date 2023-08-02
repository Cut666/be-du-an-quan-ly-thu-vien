package com.ntt.spring.security.login.security.services.itp;

import com.ntt.spring.security.login.models.dto.RentalDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Rental;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RentalService {
    public String generateReceiptCode(Long id);
    public Object getDepositPercentage(Long id);
    public Object getByCode(Long id,String cccd);
    public Object findByCode(Long id,String cccd);
    public Object getById(int id);
    public Object getAll(Long id);
    public Object creatRental(RentalDTO dto, String cccd);
    public Object updateRental(RentalDTO dto, String cccd);
    public Object deleteRental(int id,Long userid);
    public Object updateDepositPercentage(Long id,double d);
    public LocalDate getDate();
}
