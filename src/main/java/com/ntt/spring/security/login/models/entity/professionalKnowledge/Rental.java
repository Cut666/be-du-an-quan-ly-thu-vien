package com.ntt.spring.security.login.models.entity.professionalKnowledge;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.Servicez;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.models.fileenum.StatusRental;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private LocalDate timeOrder;
    private int dayNumber;
    private double depositPercentage;
    private LocalDate estimateTime;
    private String note;
    private StatusRental statusRental;
    private StatusPay statusPay;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Warehouse_id")
    private Warehouse warehouse;
    private double totalprice;
    @ManyToOne()
    @JoinColumn(name = "Library_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "subjects_id")
    private Subjects subjects;


}
