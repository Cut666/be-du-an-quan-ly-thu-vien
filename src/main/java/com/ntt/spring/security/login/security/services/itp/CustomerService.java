package com.ntt.spring.security.login.security.services.itp;



import com.ntt.spring.security.login.models.dto.CustomerDTO;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.fileenum.Sub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Subjects> getByPhone(String cccd,Long id, Sub sub);
    public List<Subjects> getAll(Sub sub,Long id);
    public Object creatCustomer(CustomerDTO dto);
//    public Subjects updateWalletCustomer(CustomerDTO dto);
    public Object updateCustomer(CustomerDTO dto);
    public Object deleteCustomer(int id);
    public Subjects creatCustomer1(CustomerDTO dto);
}
