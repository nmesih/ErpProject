package com.example.erpproject.databases.entity;

import com.example.erpproject.model.OrderStatus;
import com.example.erpproject.util.dbutil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "order")
@Data
@AttributeOverride(name = "uuid", column = @Column(name = "order_uuid"))
@AttributeOverride(name = "id", column = @Column(name = "order_id"))
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;
    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.WAITING_CONFIRMATION;
    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;


   public void addOrderDetails(OrderDetails orderDetails) {
        this.orderDetails.add(orderDetails);
    }
}
