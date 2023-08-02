package com.ntt.spring.security.login.models.dto;

import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Rental;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
public class ReRentalDTO {
    private int id;
    private String code;
    private LocalDate dateAdd;
    private LocalDate dateRe;
    private Long dayNumber;
    private Long numberDateOut;
    private String note;
    private Rental rental;
    private User user;
    private ReceiptDTO receiptDTO;
    private double moneyRental;
    private double fine;
    private double penaltyRate;
    private double totalprice;
    private StatusPay statusPay;
    private UNCDTO uncdto;
    private PhieuChiDTO phieuChiDTO;
    private BaoCoDTO baoCoDTO;
    private PhieuThuDTO phieuThuDTO;
}
