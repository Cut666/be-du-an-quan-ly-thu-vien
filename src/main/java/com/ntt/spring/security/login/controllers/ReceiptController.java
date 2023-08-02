package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.models.dto.ExportDTO;
import com.ntt.spring.security.login.models.dto.ReceiptDTO;
import com.ntt.spring.security.login.security.services.itp.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    public ReceiptService receiptService;
    @PostMapping("/create")
    public ResponseEntity<Object> createReceipt(@RequestBody ReceiptDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(receiptService.creatWarehouseReceipt(dto,cccd));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateReceipt(@RequestBody ReceiptDTO dto,@RequestParam String cccd){
        return ResponseEntity.ok(receiptService.updateWarehouseReceipt(dto,cccd));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteReceipt(@RequestParam int id){
        return ResponseEntity.ok(receiptService.deleteWarehouseReceipt(id));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(receiptService.generateReceiptCode(id));
    }
}
