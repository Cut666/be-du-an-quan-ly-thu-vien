package com.ntt.spring.security.login.models.dto;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.StatusBanks;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BaoCoDTO {
    private int id;
    private String code;
    private LocalDate collectionDate;
    private double money;
    private String note;
    private StatusBanks statusBanks;
    private Subjects subjects;
    private User library;
}
