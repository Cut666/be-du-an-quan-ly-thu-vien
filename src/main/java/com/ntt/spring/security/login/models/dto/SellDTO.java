package com.ntt.spring.security.login.models.dto;

import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SellDTO {
    private int id;
    private String code;
    private LocalDate paymentDate;
    private double money;
    private String note;
    private StatusBuySell statusBuySell;
    private ExportDTO exportDTO;
    private Warehouse warehouse;
    private StatusPay statusPay;
    private BaoCoDTO baoCoDTO;
    private PhieuThuDTO phieuThuDTO;
    private User user;
}
