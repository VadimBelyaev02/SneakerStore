package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import sun.plugin.util.UIUtil;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
}
