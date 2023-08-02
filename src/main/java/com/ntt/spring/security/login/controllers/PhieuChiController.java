package com.ntt.spring.security.login.controllers;


import com.ntt.spring.security.login.models.dto.PhieuChiDTO;
import com.ntt.spring.security.login.security.services.itp.PhieuChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/phieuchi")
public class PhieuChiController {
    @Autowired
    PhieuChiService phieuChiService;

    @PostMapping("/create")
    public ResponseEntity<Object> createCashFund(@RequestBody PhieuChiDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(phieuChiService.creatCashFund(dto,cccd));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateCashFund(@RequestBody PhieuChiDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(phieuChiService.updateCashFund(dto,cccd));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id,@RequestParam Long userid){
        return ResponseEntity.ok(phieuChiService.deleteCashFund(id, userid));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(phieuChiService.generateReceiptCode(id));
    }
}
