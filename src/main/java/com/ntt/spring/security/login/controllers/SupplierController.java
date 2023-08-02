package com.ntt.spring.security.login.controllers;


import com.ntt.spring.security.login.models.dto.SupplierDTO;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.fileenum.Sub;
import com.ntt.spring.security.login.security.services.itp.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    SupplierService supplierService;
    @GetMapping("/getByPhone")
    public ResponseEntity<Object> getById(@RequestParam String taxcode,@RequestParam Long id,@RequestParam Sub sub){
            return ResponseEntity.ok(supplierService.getByTaxCode(sub,id,taxcode));
    }
    @GetMapping("/getByCCCDORTax")
    public ResponseEntity<Object> getByCCCDOrTax(@RequestParam String cccd,@RequestParam Long id){
        return ResponseEntity.ok(supplierService.getByCoreOrTax(id,cccd));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(@RequestParam Sub sub, @RequestParam Long id){
        return ResponseEntity.ok(supplierService.getAll(sub,id));
    }
    @GetMapping("/getin")
    public ResponseEntity<Object> getin(@RequestParam Long id){
        return ResponseEntity.ok(supplierService.getIn(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createCustomer(@RequestBody SupplierDTO dto){
            return ResponseEntity.ok(supplierService.creatSupplier(dto));
    }
    @PostMapping("/updatewallet")
    public ResponseEntity<Object> createWallet(@RequestBody SupplierDTO dto){
        return ResponseEntity.ok(supplierService.updateWalletSupplier(dto));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateCustomer(@RequestBody SupplierDTO dto){
        return ResponseEntity.ok(supplierService.updateSupplier(dto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCustomer(@RequestParam int id){
        return ResponseEntity.ok(supplierService.deleteSupplier(id));
    }
}
