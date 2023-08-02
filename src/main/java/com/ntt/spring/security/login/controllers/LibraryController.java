package com.ntt.spring.security.login.controllers;



import com.ntt.spring.security.login.models.dto.LibraryDTO;
import com.ntt.spring.security.login.security.services.itp.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    LibraryService libraryService;
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestParam long id){
        return ResponseEntity.ok(libraryService.getUser(id));
    }
//    @CrossOrigin(origins = "http://127.0.0.1:5500")
//    @PostMapping("/create")
//    public ResponseEntity<Object> createLibrary(@RequestBody LibraryDTO libraryDTO){
//        boolean result = libraryService.creatLibrary(libraryDTO);
//        if (result){
//            return ResponseEntity.ok(libraryDTO);
//        }else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tạo tài khoản không thành công");
//        }
////        return ResponseEntity.ok(customerService.creatCustomer(customerDTO));
//    }
@CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/updatewallet")
    public ResponseEntity<Object> createWallet(@RequestBody LibraryDTO libraryDTO){
        return ResponseEntity.ok(libraryService.updateWalletLibrary(libraryDTO));
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/updatelibrary")
    public ResponseEntity<Object> updateLibrary(@RequestBody LibraryDTO libraryDTO){
        return ResponseEntity.ok(libraryService.updateLibrary(libraryDTO));
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteLibrary(@RequestParam long id){
        return ResponseEntity.ok(libraryService.deleteLibrary(id));
    }
}
