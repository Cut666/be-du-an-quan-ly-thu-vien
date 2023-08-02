package com.ntt.spring.security.login.controllers;


import com.ntt.spring.security.login.models.dto.CustomerDTO;
import com.ntt.spring.security.login.models.entity.Subjects;
import com.ntt.spring.security.login.models.fileenum.Sub;
import com.ntt.spring.security.login.repository.SubjectenumRepository;
import com.ntt.spring.security.login.repository.SubjectsRepository;
import com.ntt.spring.security.login.security.services.itp.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    SubjectenumRepository subjectenumRepository;
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/getByPhone")
    public ResponseEntity<Object> getById(@RequestParam Sub sub,@RequestParam Long id,@RequestParam String cccd){

        return ResponseEntity.ok(customerService.getByPhone(cccd,id,sub));
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/getAllSub")
    public ResponseEntity<Object> getAllSub(){
        return ResponseEntity.ok(subjectenumRepository.findAll());
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping ("/getAll")
    public ResponseEntity<Object> getAll(@RequestParam Sub sub, @RequestParam Long id){
        return ResponseEntity.ok(customerService.getAll(sub,id));
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/create")
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO customerDTO){
       return ResponseEntity.ok(customerService.creatCustomer(customerDTO));
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/updatecustomer")
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDTO customerDTO){
        return ResponseEntity.ok(customerService.updateCustomer(customerDTO));
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCustomer(@RequestParam int id){
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }
}
