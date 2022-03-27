package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Cart;
import com.vadim.sneakerstore.entity.Favorite;
import com.vadim.sneakerstore.entity.ProductCustomerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, ProductCustomerId> {

    Optional<Cart> findByCustomerIdAndProductId(UUID customerId, UUID productId);

    boolean existsByCustomerIdAndProductId(UUID customerId, UUID productId);

    void deleteByCustomerIdAndProductId(UUID customerId, UUID productId);

    List<Cart> findAllByCustomerId(UUID customerId);
}
