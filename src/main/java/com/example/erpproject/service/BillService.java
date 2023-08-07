package com.example.erpproject.service;

import com.example.erpproject.databases.entity.*;
import com.example.erpproject.databases.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillService {

    @Autowired
    BillRepository billRepository;

    public Bill createBill(Order order) {
        Bill bill = new Bill();
        bill.setOrder(order);
        order.setBill(bill);

        billRepository.save(bill);

        double totalPriceWithKdv = 0.0;
        double totalPriceWithoutKdv = 0.0;


        List<OrderDetails> orderDetailsList = order.getOrderDetails();
        for (OrderDetails orderDetails : orderDetailsList) {
            totalPriceWithKdv += calculatePriceWithKdv(orderDetails);
            totalPriceWithoutKdv += calculatePriceWithoutKdv(orderDetails);
            System.out.println("Sipariş edilen ürün: " + orderDetails.getProduct().getName() + ", Adet: " + orderDetails.getQuantity()
                    + ", Birim fiyatı: " + orderDetails.getProduct().getPrice());
        }
        bill.setTotalPriceWithKdv(totalPriceWithKdv);
        bill.setTotalPriceWithoutKdv(totalPriceWithoutKdv);
        bill.setTotalKdvAmount(totalPriceWithKdv - totalPriceWithoutKdv);
        return bill;
    }

    public double calculatePriceWithKdv(OrderDetails orderDetails) {
        Product product = orderDetails.getProduct();

        if (product.isKdvApplied()) {
            return product.getPrice();
        } else {
            return addKdvToProduct(product);

        }

    }

    public double addKdvToProduct(Product product) {
        Kdv kdv = new Kdv();
        return product.getPrice() * (kdv.getPercent() / 100 + 1);

    }


    public double calculatePriceWithoutKdv(OrderDetails orderDetails) {
        Product product = orderDetails.getProduct();
        if (!product.isKdvApplied()) {
            return product.getPrice();
        } else {
            return subtractKdvFromProduct(product);

        }

    }


    public double subtractKdvFromProduct(Product product) {

        Kdv kdv = new Kdv();
        return product.getPrice() * (kdv.getPercent() / 100);

    }

    public Bill getBillByUuid(UUID uuid) {
        Optional<Bill> receiptEntityOptional = billRepository.findByUuid(uuid);

        return receiptEntityOptional.orElse(null);
    }
}
