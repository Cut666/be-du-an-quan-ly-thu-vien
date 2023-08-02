package com.ntt.spring.security.login.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Subjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
    private String gmail;
    private String CCCD;
    private String taxcode;
    private String address;
    @ManyToMany
    @JoinTable(name = "subjects_subjectenum", joinColumns = @JoinColumn(name = "subjects_id"), inverseJoinColumns = @JoinColumn(name = "subjectenum_id"))
    private List<Subjectenum> subjectenums;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
