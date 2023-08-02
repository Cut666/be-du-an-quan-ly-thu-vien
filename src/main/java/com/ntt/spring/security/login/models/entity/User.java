package com.ntt.spring.security.login.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntt.spring.security.login.models.entity.Role;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.BuySellBook;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.ReRental;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Rental;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.Warehouse;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  private String name;
  private String phone;
  private String tax;
  private String address;
  private double depositPercentage;
  private Long datemaxRental;
  private double rate;

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
  @JsonBackReference
  @OneToMany(mappedBy = "user")
  private List<Product> products;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "wallet_id")
  private Wallet wallet;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cash_id")
  private Cash cash;
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Subjects> subjects;
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Warehouse> warehouses;
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<BuySellBook> buySellBooks;
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Rental> rentals;
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<ReRental> reRentals;
}
