package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.models.entity.professionalKnowledge.WarehouseProduct;
import com.ntt.spring.security.login.security.services.itp.WarehouseProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/WarehouseProduct")
public class WarehouseProductController {
    @Autowired
    public WarehouseProductService warehouseProductService;
    @GetMapping("getall")
    public ResponseEntity<Object> getAll(@RequestParam Long userId){
        return ResponseEntity.ok(warehouseProductService.getWarehouseProductsByUserId(userId));
    }
    @GetMapping("getbycode")
    public ResponseEntity<Object> getAll(@RequestParam Long userId,@RequestParam String warehouseCode){
        return ResponseEntity.ok(warehouseProductService.getWarehouseProductsByUserIdAndWarehouseCode(userId,warehouseCode));
    }
    @GetMapping("getbybuysell")
    public ResponseEntity<Object> getbybuysell(@RequestParam int id){
        return ResponseEntity.ok(warehouseProductService.findWarehouseProductsByBuySellBook(id));
    }
    @GetMapping("getbyrental")
    public ResponseEntity<Object> getbyrental(@RequestParam int id){
        return ResponseEntity.ok(warehouseProductService.findWarehouseProductsByRental(id));
    }
    @GetMapping("getbyreRental")
    public ResponseEntity<Object> getbyreRental(@RequestParam int id){
        return ResponseEntity.ok(warehouseProductService.findWarehouseProductsByReRental(id));
    }
    @GetMapping("getbyProductRerental")
    public ResponseEntity<Object> getbyProductRerental(@RequestParam Long id,@RequestParam String code){
        return ResponseEntity.ok(warehouseProductService.findProductsByReRental(id,code));
    }
    @GetMapping("getbyWarehouseProduct")
    public ResponseEntity<Object> getbyWarehouseProduct(@RequestParam Long id,@RequestParam String code){
        return ResponseEntity.ok(warehouseProductService.findWarehouseProductsByReRental(id,code));
    }
}
