package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.dto.OrderDto;
import com.vadim.sneakerstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM orders " +
            "WHERE customer_id =:customerId")
    List<Order> findAllByCustomerId(UUID customerId);
}
