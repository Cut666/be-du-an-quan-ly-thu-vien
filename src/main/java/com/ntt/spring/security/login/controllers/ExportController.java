package com.ntt.spring.security.login.controllers;


import com.ntt.spring.security.login.models.dto.ExportDTO;
import com.ntt.spring.security.login.security.services.itp.ExportService;
import com.ntt.spring.security.login.security.services.itp.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/export")
public class ExportController {
    @Autowired
    public ExportService exportService;
    @Autowired
    public WarehouseService warehouseService;
    @GetMapping("/getbycode")
    public ResponseEntity<Object> getByCode(@RequestParam String code){
        return ResponseEntity.ok(exportService.getByCode(code));
    }
    @GetMapping("/getbyid")
    public ResponseEntity<Object> getById(@RequestParam int id){
        return ResponseEntity.ok(warehouseService.getById(id));
    }
    @GetMapping("generateCode")
    public ResponseEntity<Object> generateCode(@RequestParam Long id){
        return ResponseEntity.ok(exportService.generateReceiptCode(id));
    }
    @GetMapping("/getbywarehouse")
    public ResponseEntity<Object> getByWarehouse(@RequestParam int id){
        return ResponseEntity.ok(warehouseService.getByIdWarehouse(id));
    }
    @GetMapping("/getbywarehousecode")
    public ResponseEntity<Object> getByWarehousecode(@RequestParam Long id,@RequestParam String warehouseCode){
        return ResponseEntity.ok(warehouseService.getWarehousesByUserIdAndCode(id,warehouseCode));
    }
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(@RequestParam long id){
        return ResponseEntity.ok(exportService.getAll(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createReceipt(@RequestBody ExportDTO dto,@RequestParam String cccd){
        return ResponseEntity.ok(exportService.creatWarehouse(dto,cccd));
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateReceipt(@RequestBody ExportDTO dto,@RequestParam String cccd){
        return ResponseEntity.ok(exportService.updateWarehouse(dto,cccd));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteReceipt(@RequestParam int id){
        return ResponseEntity.ok(exportService.deleteWarehouseExport(id));
    }
}
