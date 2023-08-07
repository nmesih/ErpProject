package com.example.erpproject.databases.entity;

import com.example.erpproject.util.dbutil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table
@AttributeOverride(name= "uuid", column = @Column(name="customer_uuid"))
@Data
public class Bill extends BaseEntity {

    @Column
    private int billNumber;
    @Column
    private double totalPriceWithKdv;
    @Column
    private double totalKdvAmount;
    @Column
    private double totalPriceWithoutKdv;
    @OneToOne(mappedBy = "bill")
    private Order order;
}
