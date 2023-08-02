package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.models.dto.PhieuChiDTO;
import com.ntt.spring.security.login.models.dto.PhieuThuDTO;
import com.ntt.spring.security.login.security.services.itp.PhieuChiService;
import com.ntt.spring.security.login.security.services.itp.PhieuThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/phieuthu")
public class PhieuThuController {
    @Autowired
    PhieuThuService phieuThuService;

    @PostMapping("/create")
    public ResponseEntity<Object> createCashFund(@RequestBody PhieuThuDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(phieuThuService.creatCashFund(dto,cccd));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateCashFund(@RequestBody PhieuThuDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(phieuThuService.updateCashFund(dto,cccd));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id,@RequestParam Long userid){
        return ResponseEntity.ok(phieuThuService.deleteCashFund(id, userid));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(phieuThuService.generateReceiptCode(id));
    }
}
