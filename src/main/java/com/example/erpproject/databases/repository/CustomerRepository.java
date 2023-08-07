package com.example.erpproject.databases.repository;

import com.example.erpproject.databases.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    @Override
    List<Customer> findAll();

    @Modifying
    void deleteByUuid(UUID uuid);

    Optional<Customer> findByUuid(UUID uuid);

}
