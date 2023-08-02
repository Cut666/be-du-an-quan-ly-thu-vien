package com.ntt.spring.security.login.models.dto;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.StatusBanks;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UNCDTO {
    private int id;
    private String code;
    private LocalDate paymentDate;
    private String note;
    private StatusBanks statusBanks;
    private double money;
    private Subjects subjects;
    private User library;
}
