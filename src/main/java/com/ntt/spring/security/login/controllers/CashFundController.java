package com.ntt.spring.security.login.controllers;


import com.ntt.spring.security.login.security.services.itp.CashFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/cashfund")
public class CashFundController {
    @Autowired
    CashFundService cashFundService;
    @GetMapping("/getbycode")
    public ResponseEntity<Object> getByCode(@RequestParam String code, @RequestParam long id){
        return ResponseEntity.ok(cashFundService.getByCode(code,id));
    }
    @GetMapping("/getbyid")
    public ResponseEntity<Object> getByCode(@RequestParam int id){
        return ResponseEntity.ok(cashFundService.getById(id));
    }
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(@RequestParam long id){
        return ResponseEntity.ok(cashFundService.getAll(id));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id){
        return ResponseEntity.ok(cashFundService.delete(id));
    }
//    @PostMapping("/create")
//    public ResponseEntity<Object> createCashFund(@RequestBody PhieuChiDTO dto){
//        return ResponseEntity.ok(phieuChiService.creatCashFund(dto));
//    }
//    @PostMapping("/update")
//    public ResponseEntity<Object> updateCashFund(@RequestBody PhieuChiDTO dto){
//        return ResponseEntity.ok(phieuChiService.updateCashFund(dto));
//    }
//    @PostMapping("/delete")
//    public ResponseEntity<Object> deleteCashFund(@RequestParam String code){
//        return ResponseEntity.ok(phieuChiService.deleteCashFund(code));
//    }
}
