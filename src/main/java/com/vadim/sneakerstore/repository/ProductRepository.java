package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);
}
