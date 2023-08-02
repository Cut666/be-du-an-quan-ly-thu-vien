package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.models.dto.ReRentalDTO;
import com.ntt.spring.security.login.security.services.itp.ReRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/reRental")
public class ReRentalController {
    @Autowired
    ReRentalService reRentalService;
    @GetMapping("getall")
    public ResponseEntity<Object> getAll(@RequestParam Long id){
        return ResponseEntity.ok(reRentalService.getAll(id));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(reRentalService.generateReceiptCode(id));
    }
    @GetMapping("getcode")
    public ResponseEntity<Object> getCode(@RequestParam Long id,@RequestParam String cccd){
        return ResponseEntity.ok(reRentalService.getByCode(id,cccd));
    }
    @GetMapping("updateStatusReRental")
    public ResponseEntity<Object> updateStatusReRental(@RequestParam Long id,@RequestParam String code){
        return ResponseEntity.ok(reRentalService.updateStatusReRental(code,id));
    }
    @GetMapping("getid")
    public ResponseEntity<Object> getId(@RequestParam int id){
        return ResponseEntity.ok(reRentalService.getById(id));
    }

    @GetMapping("getDate")
    public ResponseEntity<Object> getDate(){
        return ResponseEntity.ok(reRentalService.getDate());
    }
    @PostMapping("create")
    public ResponseEntity<Object> createRental(@RequestBody ReRentalDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(reRentalService.creatReRental(dto,cccd));
    }
    @PostMapping("update")
    public ResponseEntity<Object> updateRental(@RequestBody ReRentalDTO dto, @RequestParam String cccd){
        return ResponseEntity.ok(reRentalService.updateReRental(dto,cccd));
    }
    @GetMapping("updateRate")
    public ResponseEntity<Object> updateUser(@RequestParam Long userid, @RequestParam double d){
        return ResponseEntity.ok(reRentalService.updateRate(userid,d));
    }
    @GetMapping("getRate")
    public ResponseEntity<Object> getDepositPercentage(@RequestParam Long id){
        return ResponseEntity.ok(reRentalService.getRate(id));
    }
    @GetMapping("updatemaxdate")
    public ResponseEntity<Object> updatemaxdate(@RequestParam Long userid, @RequestParam Long d){
        return ResponseEntity.ok(reRentalService.updateMaxDay(userid,d));
    }
    @GetMapping("getmaxdate")
    public ResponseEntity<Object> getmaxdate(@RequestParam Long id){
        return ResponseEntity.ok(reRentalService.getMaxDay(id));
    }
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteRental(@RequestParam int id){
        return ResponseEntity.ok(reRentalService.deleteReRental(id));
    }
    @GetMapping("lostbool")
    public ResponseEntity<Object> lostbool(@RequestParam Long id,@RequestParam String cccd){
        return ResponseEntity.ok(reRentalService.lostBook(cccd,id));
    }
}
