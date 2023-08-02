package com.ntt.spring.security.login.models.entity.professionalKnowledge;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.fileenum.StatusWarehouse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private LocalDate dateadd;
    private LocalDate exportdate;
    private String note;
    private StatusWarehouse statusWareHouse;
    @ManyToOne()
    @JoinColumn(name = "subjects_id")
    private Subjects subjects;
//    @JsonBackReference
//    @ManyToMany
//    @JoinTable(name = "Warehouse_product", joinColumns = @JoinColumn(name = "warehouse_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
//    private List<Product> products;
    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<WarehouseProduct>warehouseProducts;
    @ManyToOne()
    @JoinColumn(name = "Library_id")
    private User user;

}
