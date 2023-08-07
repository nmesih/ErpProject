package com.example.erpproject.databases.repository;

import com.example.erpproject.databases.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Modifying
    void deleteByUuid(UUID uuid);

    Optional<Bill> findByUuid(UUID uuid);
}
