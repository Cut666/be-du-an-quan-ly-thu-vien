package com.ntt.spring.security.login.models.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatusDTO {
    private String status;
    private String message;
    private String data;
}
