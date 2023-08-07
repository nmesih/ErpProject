package com.example.erpproject.databases.repository;

import com.example.erpproject.databases.entity.Customer;
import com.example.erpproject.databases.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    List<Order> findAll();

    Optional<Order> findById(int id);

    Optional<Order> findByUuid(UUID uuid);
}
