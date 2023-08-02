package com.ntt.spring.security.login.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import com.ntt.spring.security.login.models.fileenum.StatusProduct;
import com.ntt.spring.security.login.models.fileenum.StatusProductNew;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table
@Entity
@Getter
@Setter
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String code;
private String name;
private int quantity;
private double price;
private double priceRental;
@Enumerated(EnumType.STRING)
private StatusProduct statusProduct;
@Enumerated(EnumType.STRING)
private StatusProductNew statusProductNew;
@OneToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "unit_id")
private Unit unit;
@JsonBackReference
@OneToMany(mappedBy = "product")
private List<WarehouseProduct> warehouseProducts;
@NotNull
@ManyToOne()
@JoinColumn(name = "user_id")
private User user;

}
