package com.ntt.spring.security.login.security.services.itp;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BuySellService {
    public Object getAll(Long id);
    public Object findCode(Long id,String cccd);
    public Object getId(int id);
}
