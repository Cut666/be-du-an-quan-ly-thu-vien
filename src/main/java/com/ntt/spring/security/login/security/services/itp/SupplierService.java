package com.ntt.spring.security.login.security.services.itp;



import com.ntt.spring.security.login.models.dto.SupplierDTO;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.fileenum.Sub;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {
    public List<Subjects> getByTaxCode(Sub sub,Long id,String taxcode);
    public List<Subjects> getAll(Sub sub, Long id);
    public Object creatSupplier(SupplierDTO dto);
    public Subjects updateWalletSupplier(SupplierDTO dto);
    public Object updateSupplier(SupplierDTO dto);
    public Object deleteSupplier(int id);
    public Subjects creatSupplier1(SupplierDTO dto);
    public Object getByCoreOrTax(long id, String code);
    public List<Subjects> getIn(Long id);
}
