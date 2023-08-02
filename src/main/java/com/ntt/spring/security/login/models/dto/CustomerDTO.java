package com.ntt.spring.security.login.models.dto;



import com.ntt.spring.security.login.models.entity.Subjectenum;
import com.ntt.spring.security.login.models.entity.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CustomerDTO {
    private int id;
    private String name;
    private String phone;
    private String gmail;
    @NotNull
    private String CCCD;
    private String address;
    private List<Subjectenum> subjectenums;
    private User user;
//    private Wallet wallet;
}
