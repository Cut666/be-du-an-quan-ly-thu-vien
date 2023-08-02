package com.ntt.spring.security.login.models.entity.professionalKnowledge;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ntt.spring.security.login.models.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class WarehouseProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private double price;
    private double total;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    public WarehouseProduct(Product product) {
        this.quantity = product.getQuantity();
        this.product = product;
        this.price = product.getPrice();
        this.calculateTotal();
    }

    public WarehouseProduct() {
    }
    private void calculateTotal() {
        this.total = this.quantity * this.price;
    }
}
