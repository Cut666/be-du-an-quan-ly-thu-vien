package com.ntt.spring.security.login.controllers;


import com.ntt.spring.security.login.models.entity.Product;
import com.ntt.spring.security.login.security.services.itp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public ProductService productService;
    @GetMapping("/getbycode")
    public ResponseEntity<Object> getByCode(@RequestParam String name,@RequestParam long id){
        return ResponseEntity.ok(productService.getByName(id,name));
    }
    @GetMapping("/getbycodes")
    public ResponseEntity<Object> getByCode1(@RequestParam String name,@RequestParam long id){
        return ResponseEntity.ok(productService.getByCode1(id,name));
    }
    @GetMapping("/generateCustomerCode")
    public ResponseEntity<Object> generateCustomerCode(@RequestParam long id){
        return ResponseEntity.ok(productService.generateCustomerCode(id));
    }
    @GetMapping("/getbycodeforRental")
    public ResponseEntity<Object> getbycodeforRental(@RequestParam String name,@RequestParam long id){
        return ResponseEntity.ok(productService.getByCodeRental(id,name));
    }
    @GetMapping("/getallbyforRental")
    public ResponseEntity<Object> getallbyforRental(@RequestParam long id){
        return ResponseEntity.ok(productService.getAllByRental(id));
    }
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(@RequestParam long id){
        return ResponseEntity.ok(productService.getAll(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.creatProduct(product));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateProduct(@RequestBody Product dto){
        return ResponseEntity.ok(productService.updateProduct(dto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteProduct(@RequestParam int id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
    @GetMapping("/listproduct")
    public ResponseEntity<Object> listproduct(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, @RequestParam String code, @RequestParam long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate){
        return ResponseEntity.ok(productService.getWarehouseProductByConditionsin(id,code,startDate,endDate));
    }
    @GetMapping("/quantityproduct")
    public ResponseEntity<Object> quantityproduct( @RequestParam String code, @RequestParam long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate){
        return ResponseEntity.ok(productService.getquantityProductByConditions(id,code,startDate));
    }
    @GetMapping("/FirstDayOfMonth")
    public ResponseEntity<Object> FirstDayOfMonth(){
        return ResponseEntity.ok(productService.getFirstDayOfMonth());
    }
    @GetMapping("/LastDayOfMonth")
    public ResponseEntity<Object> LastDayOfMonth(){
        return ResponseEntity.ok(productService.getLastDayOfMonth());
    }
}
