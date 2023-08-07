package com.example.erpproject.databases.entity;

import com.example.erpproject.util.dbutil.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "customer")
@AttributeOverride(name = "uuid", column = @Column(name = "customer_uuid"))
@AttributeOverride(name = "id", column = @Column(name = "customer_id"))
@Data
public class Customer extends BaseEntity {

        @Column(name = "name")
        private String name;
        @Column(name = "surname")
        private String surname;
        @Column(name = "address")
        private String address;
        @Column(name = "city")
        private String city;
        @Column(name = "country")
        private String country;
        @Column(name = "postalCode")
        private int postalCode;
        @OneToMany(mappedBy = "customer")
        private List<Order> orders;


        public void addOrder(Order order) {
                this.orders.add(order);
                order.setCustomer(this);
        }
}
