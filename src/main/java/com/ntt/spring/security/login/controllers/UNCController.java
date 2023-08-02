package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.models.dto.PhieuChiDTO;
import com.ntt.spring.security.login.models.dto.UNCDTO;
import com.ntt.spring.security.login.security.services.itp.UNCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/unc")
public class UNCController {
    @Autowired
    @Qualifier("UNCServiceImpl")
    UNCService uncService;
    @PostMapping("/create")
    public ResponseEntity<Object> createBanks(@RequestBody UNCDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(uncService.createBanks(dto,cccd));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateCashFund(@RequestBody UNCDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(uncService.updateBanks(dto,cccd));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id,@RequestParam Long userid){
        return ResponseEntity.ok(uncService.deleteBanks(id, userid));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(uncService.generateReceiptCode(id));
    }
}
