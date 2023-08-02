package com.ntt.spring.security.login.controllers;


import com.ntt.spring.security.login.models.dto.BuyDTO;
import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import com.ntt.spring.security.login.security.services.itp.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/buy")
public class BuyBookController {
    @Autowired
    BuyService buyService;

    @PostMapping("/create")
    public ResponseEntity<Object> createBuySell(@RequestBody BuyDTO dto,@RequestParam String cccd){
        return ResponseEntity.ok(buyService.creatBuySell(dto,cccd));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateBuySell(@RequestBody BuyDTO dto,@RequestParam String cccd){
        return ResponseEntity.ok(buyService.updateBuySell(dto,cccd));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCashFund(@RequestParam int id, @RequestParam Long userid){
        return ResponseEntity.ok(buyService.deleteBuySell(id,userid));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(buyService.generateReceiptCode(id));
    }
}
