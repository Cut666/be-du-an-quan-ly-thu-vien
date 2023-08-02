package com.ntt.spring.security.login.models.entity.professionalKnowledge;

import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.StatusPay;
import com.ntt.spring.security.login.models.fileenum.StatusReRental;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class ReRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private LocalDate dateAdd;
    private Long dayNumber;
    private double moneyRental;
    private LocalDate dateRe;
    private Long numberDateOut;
    private double penaltyRate;
    private double fine;
    private String note;
    private double totalprice;
    private StatusPay statusPay;
    private StatusReRental statusReRental;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Warehouse_id")
    private Warehouse warehouse;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Rental_id")
    private Rental rental;
    @ManyToOne()
    @JoinColumn(name = "Library_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "subjects_id")
    private Subjects subjects;
}
