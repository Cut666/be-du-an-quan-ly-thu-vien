package com.ntt.spring.security.login.models.dto;


import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import com.ntt.spring.security.login.models.fileenum.StatusWarehouse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReceiptDTO {
    private int id;
    private String code;
    private LocalDate dateadd;
    private String note;
    private StatusWarehouse statusWareHouse;
    private Subjects subjects;
    private User user;
    private List<WarehouseProduct>warehouseProducts;
}
