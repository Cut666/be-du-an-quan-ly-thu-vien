package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.security.services.itp.BuySellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/buysell")
public class BuySellBookController {
    @Autowired
    BuySellService buySellService;
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(@RequestParam Long id){
        return ResponseEntity.ok(buySellService.getAll(id));
    }
    @GetMapping("/getcode")
    public ResponseEntity<Object> getCode(@RequestParam Long id,@RequestParam String cccd){
        return ResponseEntity.ok(buySellService.findCode(id, cccd));
    }
    @GetMapping("/getid")
    public ResponseEntity<Object> getId(@RequestParam int id){
        return ResponseEntity.ok(buySellService.getId(id));
    }
}
