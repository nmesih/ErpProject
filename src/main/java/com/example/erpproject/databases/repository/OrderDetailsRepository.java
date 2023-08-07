package com.example.erpproject.databases.repository;

import com.example.erpproject.databases.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Modifying
    void deleteByUuid(UUID uuid);


    Optional<OrderDetails> findByUuid(UUID uuid);
}
