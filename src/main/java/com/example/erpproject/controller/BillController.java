package com.example.erpproject.controller;

import com.example.erpproject.databases.entity.Bill;
import com.example.erpproject.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/bill")
public class BillController {

    @Autowired
    BillService billService;

    @GetMapping("order-bill-uuid/{uuid}")
    public ResponseEntity<Bill> getBillByUUID(@PathVariable UUID uuid){
        Bill bill = billService.getBillByUuid(uuid);
        if (bill != null){
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

}
