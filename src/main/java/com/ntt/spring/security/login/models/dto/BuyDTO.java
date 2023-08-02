package com.ntt.spring.security.login.models.dto;

import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BuyDTO {
    private int id;
    private String code;
    private LocalDate collectionDate;
    private double money;
    private String note;
    private StatusBuySell statusBuySell;
    private ReceiptDTO receiptDTO;
    private Warehouse warehouse;
    private StatusPay statusPay;
    private UNCDTO uncdto;
    private PhieuChiDTO phieuChiDTO;
    private User user;

}
