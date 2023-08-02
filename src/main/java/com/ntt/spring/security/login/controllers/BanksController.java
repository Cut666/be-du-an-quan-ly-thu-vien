package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.security.services.itp.BanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/banks")
public class BanksController {
    @Autowired
    BanksService banksService;

    @GetMapping("/getbycode")
    public ResponseEntity<Object> getByCode(@RequestParam String code, @RequestParam long id){
        return ResponseEntity.ok(banksService.getByCode(code,id));
    }
    @GetMapping("/getbyid")
    public ResponseEntity<Object> getByCode(@RequestParam int id){
        return ResponseEntity.ok(banksService.getById(id));
    }
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(@RequestParam long id){
        return ResponseEntity.ok(banksService.getAll(id));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam int id){
        return ResponseEntity.ok(banksService.delete(id));
    }
}
