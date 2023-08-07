package com.example.erpproject.databases.entity;

import com.example.erpproject.util.dbutil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AttributeOverride(name = "uuid", column = @Column(name = "product_uuid"))
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
public class Product extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "isKdvApplied")
    private boolean isKdvApplied;
    @Column(name = "price")
    private Double price;
    @Column(name = "stock")
    private int stock;
    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails;
}
