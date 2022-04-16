package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Cart;
import com.vadim.sneakerstore.entity.ProductCustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, ProductCustomerId> {

    @Query(nativeQuery = true, value = "SELECT * FROM cart WHERE customer_id = :customerId" +
            " AND product_id = :sizeId")
    Optional<Cart> findByCustomerIdAndProductId(UUID customerId, UUID sizeId);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM cart WHERE " +
            "customer_id = :customerId AND product_id = :sizeId)")
    boolean existsByCustomerIdAndProductId(UUID customerId, UUID sizeId);

    @Query(nativeQuery = true, value = "DELETE FROM cart WHERE customer_id =:customerId " +
            "AND product_id = :sizeId")
    void deleteByCustomerIdAndProductId(UUID customerId, UUID sizeId);

    @Query(nativeQuery = true, value = "SELECT * FROM cart WHERE customer_id = :customerId")
    List<Cart> findAllByCustomerId(UUID customerId);
}
