package com.example.vendingmachine.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByNameIgnoreCase(String name);

    List<Product> findByTypeAndQuantityGreaterThan(ProductType type, int quantity);

}
