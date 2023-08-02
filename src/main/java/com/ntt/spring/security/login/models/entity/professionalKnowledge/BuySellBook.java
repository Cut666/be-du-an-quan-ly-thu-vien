package com.ntt.spring.security.login.models.entity.professionalKnowledge;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.StatusBuySell;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class BuySellBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String code;
    private LocalDate collectionDate;
    private LocalDate paymentDate;
    private double money;
    private String note;
    private StatusBuySell statusBuySell;
    private StatusPay statusPay;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Warehouse_id")
    private Warehouse warehouse;
    @ManyToOne()
    @JoinColumn(name = "Library_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "subjects_id")
    private Subjects subjects;


}
