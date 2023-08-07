package com.example.erpproject.controller;

import com.example.erpproject.databases.entity.Customer;
import com.example.erpproject.databases.entity.Order;
import com.example.erpproject.databases.entity.OrderDetails;
import com.example.erpproject.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("order/{id}")
    public ResponseEntity<Order> customerGetByCustomerId(@PathVariable int orderId) {
        return new ResponseEntity<>(orderService.getOrderByOrderId(orderId), HttpStatus.OK);
    }

    @GetMapping("order-list")
    public ResponseEntity<List<Order>> getAllOrderList() {
        return new ResponseEntity<>(orderService.findAll(),
                HttpStatus.OK);
    }

    @PostMapping("place-order/{customerId}/{productId}/{quantity}")
    public ResponseEntity<Order> createOrder(@PathVariable Long customerId, @PathVariable Long productId,
                                             @PathVariable int quantity) {
        try {
            Order order = orderService.createOrder(customerId, productId, quantity);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

      // order'a yeni ürün ekleme
        @PutMapping("add-item-to-order/{uuid}/{orderItemUUID}")
        public ResponseEntity<Order> addItemOrder
        (@PathVariable UUID uuid, @PathVariable UUID orderItemUUID){
            Order orderEntity = orderService.addItemToOrder(uuid,orderItemUUID);
            if (orderEntity != null){
                return new ResponseEntity<>(orderEntity,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }

        }

        @PutMapping("approve/{uuid}")
        public ResponseEntity<Order> approveOrder(@PathVariable UUID uuid){
            Order order = orderService.approveOrder(uuid);
            if (order != null){
                return new ResponseEntity<>(order,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
        }

    }

