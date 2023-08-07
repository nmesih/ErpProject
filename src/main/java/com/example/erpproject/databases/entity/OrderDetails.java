package com.example.erpproject.databases.entity;

import com.example.erpproject.util.dbutil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_details")
@Data
@AttributeOverride(name = "uuid", column = @Column(name = "order_detail_uuid"))
@AttributeOverride(name = "id", column = @Column( name = "order_detail_id"))
public class OrderDetails extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;




}
