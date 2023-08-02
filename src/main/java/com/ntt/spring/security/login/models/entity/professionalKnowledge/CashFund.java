package com.ntt.spring.security.login.models.entity.professionalKnowledge;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.StatusCashFund;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class CashFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private LocalDate collectionDate;
    private LocalDate paymentDate;
    private double money;
    private String note;
    private StatusCashFund statusCashFund;
    @ManyToOne()
    @JoinColumn(name = "subjects_id")
    private Subjects subjects;
    @ManyToOne()
    @JoinColumn(name = "Library_id")
    private User user;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BuySell_id")
    private BuySellBook buySellBook;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Rental_id")
    private Rental rental;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ReRental_id")
    private ReRental reRental;
}
