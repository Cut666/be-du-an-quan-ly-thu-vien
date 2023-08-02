package com.ntt.spring.security.login.models.dto;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.StatusCashFund;
import lombok.Data;


import java.time.LocalDate;

@Data
public class PhieuThuDTO {
    private int id;

    private LocalDate collectionDate;
        private String code;
    private String note;
    private double money;
    private StatusCashFund statusCashFund;
    private Subjects subjects;
    private User library;
}
