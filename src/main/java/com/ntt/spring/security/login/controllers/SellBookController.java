package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.models.dto.BuyDTO;
import com.ntt.spring.security.login.models.dto.SellDTO;
import com.ntt.spring.security.login.security.services.itp.BuyService;
import com.ntt.spring.security.login.security.services.itp.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/sell")
public class SellBookController {
    @Autowired
    SellService sellService;

    @PostMapping("/create")
    public ResponseEntity<Object> createBuySell(@RequestBody SellDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(sellService.creatBuySell(dto,cccd));
    }
    @PostMapping("/createlostbook")
    public ResponseEntity<Object> createlostbook(@RequestBody SellDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(sellService.creatSellLostBook(dto,cccd));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateBuySell(@RequestBody SellDTO dto,@RequestParam String cccd){
        return ResponseEntity.ok(sellService.updateBuySell(dto,cccd));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCashFund(@RequestParam int id, @RequestParam Long userid){
        return ResponseEntity.ok(sellService.deleteBuySell(id,userid));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(sellService.generateReceiptCode(id));
    }
}
