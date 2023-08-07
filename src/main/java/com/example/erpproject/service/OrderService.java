package com.example.erpproject.service;

import com.example.erpproject.databases.entity.Customer;
import com.example.erpproject.databases.entity.Order;
import com.example.erpproject.databases.entity.OrderDetails;
import com.example.erpproject.databases.entity.Product;
import com.example.erpproject.databases.repository.CustomerRepository;
import com.example.erpproject.databases.repository.OrderRepository;
import com.example.erpproject.databases.repository.ProductRepository;
import com.example.erpproject.model.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    BillService billService;


    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order getOrderByOrderId(int orderId) {

        Optional<Order> orderOptional = orderRepository.findById(orderId);

        return orderOptional.orElse(null);
    }

    public Order getOrderByUuid(UUID uuid) {

        Optional<Order> orderOptional = orderRepository.findByUuid(uuid);

        return orderOptional.orElse(null);
    }


   @Transactional
    public Order createOrder(Long customerId, Long productId, int quantity) {
        // 1. Müşteri ve ürün var mı diye kontrol edelim.
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı."));
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı."));

        // 2. Stokta yeterli ürün var mı diye kontrol edelim.
        if (product.getStock() < quantity) {
            throw new RuntimeException("Stokta yeterli ürün yok.");
        }

        // 3. Yeni sipariş oluşturalım ve veritabanına kaydedelim.
        Order order = new Order();

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setQuantity(quantity);
        orderDetails.setProduct(product);

        order.setCustomer(customer);
        order.addOrderDetails(orderDetails);
        customer.addOrder(order);

        orderRepository.save(order);

        // 4. Stoktan ürünü düşürelim.
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        return order;
    }


    public Order addItemToOrder(UUID uuid, UUID orderDetailsUuid){
        Order order = getOrderByUuid(uuid);
        OrderDetails orderDetails = orderDetailsService.getOrderDetailsByUUID(orderDetailsUuid);
        if (orderDetails.getProduct().getStock()>0){
            order.getOrderDetails().add(orderDetails);
            orderDetails.setOrder(order);

            return order;
        }

        return null;
    }

    public Order approveOrder(UUID uuid){
        Order order = getOrderByUuid(uuid);
        boolean stockStatus = allItemsInStock(order.getOrderDetails());

        if (stockStatus){
            updateStockOfOrder(order.getOrderDetails());
            order.setOrderStatus(OrderStatus.CONFIRMED);
            billService.createBill(order);
            return order;
        }else {
            order.setOrderStatus(OrderStatus.REJECTED);
            System.out.println("Sipariş ürünün stokta kalmaması nedeniyle iptal edildi.");
            return null;
        }

    }

    public void updateStockOfOrder(List<OrderDetails> orderDetailsList){
        for (OrderDetails orderDetails : orderDetailsList){
            orderDetails.getProduct().setStock(orderDetails.getProduct().getStock()-orderDetails.getQuantity());
        }
    }

    public boolean allItemsInStock(List<OrderDetails> orderDetailsList){

        for (OrderDetails orderDetails : orderDetailsList){
            if (orderDetails.getProduct().getStock() < 0){
                return false;
            }
        } return true;
    }

}
