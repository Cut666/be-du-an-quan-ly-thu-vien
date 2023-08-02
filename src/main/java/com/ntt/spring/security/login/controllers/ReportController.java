package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.security.services.itp.CashService;
import com.ntt.spring.security.login.security.services.itp.ReportService;
import com.ntt.spring.security.login.security.services.itp.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    CashService cashService;
    @Autowired
    WalletService walletService;

    @GetMapping("/collect")
    public ResponseEntity<Object> getcollectt(@RequestParam String code, @RequestParam long id,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate){
        return ResponseEntity.ok(reportService.getcollectbefor(id,code,startDate));
    }
    @GetMapping("/spend")
    public ResponseEntity<Object> getspend(@RequestParam String code, @RequestParam long id,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate){
        return ResponseEntity.ok(reportService.getspendbefor(id,code,startDate));
    }
    @GetMapping("/collectin")
    public ResponseEntity<Object> getcollecttin(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,@RequestParam String code, @RequestParam long id,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate){
        return ResponseEntity.ok(reportService.getcollectin(id,code,startDate,endDate));
    }
    @GetMapping("/spendin")
    public ResponseEntity<Object> getspendin(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,@RequestParam String code, @RequestParam long id,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate){
        return ResponseEntity.ok(reportService.getspendin(id,code,startDate,endDate));
    }
    @GetMapping("/totalmoneysell")
    public ResponseEntity<Object> totalmoneysell(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam long id){
        return ResponseEntity.ok(reportService.getTotalMoneySell(id,startDate));
    }
    @GetMapping("/listsell")
    public ResponseEntity<Object> listsell(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam long id){
        return ResponseEntity.ok(reportService.getListBuySellBook(id,startDate,endDate));
    }
    @GetMapping("/totalmoneyrerental")
    public ResponseEntity<Object> totalmoneyrerental(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam long id){
        return ResponseEntity.ok(reportService.getTotalMoneyReRental(id,startDate));
    }
    @GetMapping("/listrerental")
    public ResponseEntity<Object> listrerental(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam long id){
        return ResponseEntity.ok(reportService.getListReRental(id,startDate,endDate));
    }
    @GetMapping("/moneycash")
    public ResponseEntity<Object> moneycash(@RequestParam long id){
        return ResponseEntity.ok(cashService.getmoney(id));
    }
    @GetMapping("/moneywallet")
    public ResponseEntity<Object> moneywallet(@RequestParam long id){
        return ResponseEntity.ok(walletService.getmoney(id));
    }
}
