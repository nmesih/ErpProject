package com.example.erpproject.databases.repository;

import com.example.erpproject.databases.entity.Customer;
import com.example.erpproject.databases.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);


    @Override
    List<Product> findAll();

    @Modifying
    void deleteByUuid(UUID uuid);
    Optional<Product> findByUuid(UUID uuid);

    @Modifying
    @Query("UPDATE Product p SET p.price = ?1 WHERE p.id = ?2")
    void updateProductPrice(BigDecimal priceWithoutKdv, Long id);

}
