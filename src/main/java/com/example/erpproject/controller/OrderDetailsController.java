package com.example.erpproject.controller;

import com.example.erpproject.databases.entity.OrderDetails;
import com.example.erpproject.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/order-details")
public class OrderDetailsController {

    @Autowired
    OrderDetailsService orderDetailsService;

    @PostMapping()
    public ResponseEntity<OrderDetails> createOrderItem(@PathVariable UUID productUUID, @PathVariable Integer quantity){
        OrderDetails orderItem1 = orderDetailsService.createOrderDetails(productUUID,quantity);
        return new ResponseEntity<>(orderItem1, HttpStatus.CREATED);

    }

    @GetMapping("order-detail-uuid/{uuid}")
    public ResponseEntity<OrderDetails> getOrderItemByUUID(@PathVariable UUID uuid){
        OrderDetails orderDetails = orderDetailsService.getOrderDetailsByUUID(uuid);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);

    }
}
