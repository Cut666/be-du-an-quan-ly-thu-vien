package com.ntt.spring.security.login.security.services.itp;



import com.ntt.spring.security.login.models.entity.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UnitService {
    public Unit getByName(String name);
    public List<Unit> getAll();
    public Unit createUnit(Unit unit);
    public Unit updateUnit(Unit unit);
    public String deleteUnit(String name);
    public Unit createUnitProduct(Unit unit);
}
