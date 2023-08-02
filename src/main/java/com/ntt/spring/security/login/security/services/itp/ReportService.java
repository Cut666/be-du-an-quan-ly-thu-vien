package com.ntt.spring.security.login.security.services.itp;

import com.ntt.spring.security.login.models.entity.professionalKnowledge.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ReportService {
    public double getcollectbefor(Long userid, String code,LocalDate startDate);
    public double getspendbefor(Long userid, String code,LocalDate startDate);
    public double getcollectin(Long userid, String code,LocalDate startDate,LocalDate endDate);
    public double getspendin(Long userid, String code,LocalDate startDate,LocalDate endDate);
    public double getTotalMoneySell(Long userId, LocalDate endDate);
    public List<BuySellBook> getListBuySellBook(Long userId, LocalDate startDate,LocalDate endDate);
    public double getTotalMoneyReRental(Long userId, LocalDate endDate);
    public List<ReRental> getListReRental(Long userId, LocalDate startDate,LocalDate endDate);
}
