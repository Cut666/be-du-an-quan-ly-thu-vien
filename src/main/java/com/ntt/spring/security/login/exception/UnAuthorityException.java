package com.ntt.spring.security.login.exception;

public class UnAuthorityException extends RuntimeException {
    public UnAuthorityException(String message) {
        super(message);
    }
}
