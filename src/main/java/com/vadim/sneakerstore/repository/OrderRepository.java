package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {


}
