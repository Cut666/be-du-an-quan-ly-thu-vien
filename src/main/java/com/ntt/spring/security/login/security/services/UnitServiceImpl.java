package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.entity.Unit;
import com.ntt.spring.security.login.repository.UnitRepository;
import com.ntt.spring.security.login.security.services.itp.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitService unitService;
    @Autowired
    private UnitRepository unitRepository;
    @Override
    public Unit getByName(String name) {
        Unit unit = unitRepository.findByName(name);
        return unit;
    }

    @Override
    public List<Unit> getAll() {
        return unitRepository.findAll();
    }

    @Override
    public Unit createUnit(Unit unit) {
        Unit unit1=new Unit();
        unit1.setName(unit.getName());
        unitRepository.save(unit1);
        return unit1;
    }

    @Override
    public Unit updateUnit(Unit unit) {
        Unit unit1 = unitRepository.findByName(unit.getName());
        unit1.setName(unit.getName());
        unitRepository.save(unit1);
        return unit1;
    }

    @Override
    public String deleteUnit(String name) {
        Unit unit = unitRepository.findByName(name);
        unitRepository.delete(unit);
        return "xoa thanh cong";
    }
    @Override
    public Unit createUnitProduct(Unit unit) {
        Unit unit1 = unitRepository.findByName(unit.getName());
        if (unit1 == null){
            Unit unit2 = unitService.createUnit(unit);
            return unit2;
        }else {
            Unit unit3=new Unit();
            unit1.setName(unit.getName());
            unitRepository.save(unit3);
            return unit3;
        }
    }
}
