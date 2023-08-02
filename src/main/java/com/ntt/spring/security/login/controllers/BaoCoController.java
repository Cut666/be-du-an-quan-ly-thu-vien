package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.models.dto.BaoCoDTO;
import com.ntt.spring.security.login.models.dto.PhieuThuDTO;
import com.ntt.spring.security.login.security.services.itp.BaoCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/baoco")
public class BaoCoController {
    @Autowired
    BaoCoService baoCoService;

    @PostMapping("/create")
    public ResponseEntity<Object> createBanks(@RequestBody BaoCoDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(baoCoService.createBanks(dto,cccd));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateBanks(@RequestBody BaoCoDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(baoCoService.updateBanks(dto,cccd));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id,@RequestParam Long userid){
        return ResponseEntity.ok(baoCoService.deleteBanks(id, userid));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(baoCoService.generateReceiptCode(id));
    }
}
