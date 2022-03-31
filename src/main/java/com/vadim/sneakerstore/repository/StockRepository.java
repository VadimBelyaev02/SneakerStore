package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
}
