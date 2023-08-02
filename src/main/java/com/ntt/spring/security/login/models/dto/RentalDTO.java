package com.ntt.spring.security.login.models.dto;

import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.models.fileenum.StatusRental;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
public class RentalDTO {
    private int id;
    private int dayNumber;
    private String note;
    private String code;
    private double depositPercentage;
    private StatusRental statusRental;
    private ExportDTO exportDTO;
    private double totalprice;
    private User user;
    private StatusPay statusPay;
    private BaoCoDTO baoCoDTO;
    private PhieuThuDTO phieuThuDTO;
}
