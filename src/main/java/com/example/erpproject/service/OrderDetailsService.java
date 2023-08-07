package com.example.erpproject.service;

import com.example.erpproject.databases.entity.OrderDetails;
import com.example.erpproject.databases.entity.Product;
import com.example.erpproject.databases.repository.OrderDetailsRepository;
import com.example.erpproject.databases.repository.OrderRepository;
import com.example.erpproject.databases.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    public OrderDetails createOrderDetails(UUID productUuid, int quantity){
        Product product = productService.getProductByUUID(productUuid);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setProduct(product);
        orderDetails.setQuantity(quantity);


        orderDetailsRepository.save(orderDetails);
        return orderDetails;
    }

    public OrderDetails getOrderDetailsByUUID(UUID uuid){
        Optional<OrderDetails> orderItemEntityOptional = orderDetailsRepository.findByUuid(uuid);
        return orderItemEntityOptional.orElse(null);
    }
}
