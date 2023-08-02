package com.ntt.spring.security.login.models.dto;


import com.ntt.spring.security.login.models.entity.User;
import lombok.Data;
import com.ntt.spring.security.login.models.entity.Subjectenum;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SupplierDTO {
    private int id;
    private String name;
    private String phone;
    private String gmail;
    private String taxcode;
    private String CCCD;
    private String address;
    private List<Subjectenum> subjectenums;
    private User user;
}
