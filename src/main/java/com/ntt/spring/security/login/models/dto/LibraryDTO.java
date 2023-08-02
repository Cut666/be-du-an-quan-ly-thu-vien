package com.ntt.spring.security.login.models.dto;



import com.ntt.spring.security.login.models.entity.Cash;
import com.ntt.spring.security.login.models.entity.Wallet;
import lombok.Data;

@Data
public class LibraryDTO {
    private Long id;
    private String name;
    private String tax;
    private String phone;
    private String email;
    private String address;
    private Wallet wallet;
    private Cash cash;

}
