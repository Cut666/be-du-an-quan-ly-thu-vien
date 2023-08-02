package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.models.dto.RentalDTO;
import com.ntt.spring.security.login.security.services.itp.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/rental")
public class RentalController {
    @Autowired
    RentalService rentalService;
    @GetMapping("getall")
    public ResponseEntity<Object> getAll(@RequestParam Long id){
        return ResponseEntity.ok(rentalService.getAll(id));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(rentalService.generateReceiptCode(id));
    }
    @GetMapping("getcode")
    public ResponseEntity<Object> getCode(@RequestParam Long id,@RequestParam String cccd){
        return ResponseEntity.ok(rentalService.getByCode(id,cccd));
    }
    @GetMapping("findbycode")
    public ResponseEntity<Object> findbycode(@RequestParam Long id,@RequestParam String cccd){
        return ResponseEntity.ok(rentalService.findByCode(id,cccd));
    }
    @GetMapping("getid")
    public ResponseEntity<Object> getId(@RequestParam int id){
        return ResponseEntity.ok(rentalService.getById(id));
    }

    @GetMapping("getDate")
    public ResponseEntity<Object> getDate(){
        return ResponseEntity.ok(rentalService.getDate());
    }
    @PostMapping("create")
    public ResponseEntity<Object> createRental(@RequestBody RentalDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(rentalService.creatRental(dto,cccd));
    }
    @PostMapping("update")
    public ResponseEntity<Object> updateRental(@RequestBody RentalDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(rentalService.updateRental(dto,cccd));
    }
    @GetMapping("updateuser")
    public ResponseEntity<Object> updateUser(@RequestParam Long userid, @RequestParam double d){
        return ResponseEntity.ok(rentalService.updateDepositPercentage(userid,d));
    }
    @GetMapping("getDepositPercentage")
    public ResponseEntity<Object> getDepositPercentage(@RequestParam Long id){
        return ResponseEntity.ok(rentalService.getDepositPercentage(id));
    }
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteRental(@RequestParam Long userid,@RequestParam int id){
        return ResponseEntity.ok(rentalService.deleteRental(id, userid));
    }
}
