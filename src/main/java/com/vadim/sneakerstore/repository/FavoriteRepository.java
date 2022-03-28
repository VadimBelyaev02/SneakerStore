package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Favorite;
import com.vadim.sneakerstore.entity.ProductCustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, ProductCustomerId> {

    @Query(nativeQuery = true, value = "SELECT * FROM favorites WHERE customer_id = :customerId")
    List<Favorite> findAllByCustomerId(UUID customerId);

  //  @Query(nativeQuery = true, value = "SELECT * ")
  //  boolean existsByCustomerIdAndProductId(UUID customerId, UUID productId);

    @Query(nativeQuery = true, value = "SELECT * FROM favorites WHERE customer_id = :customerId" +
            " AND product_id = :productId")
    Optional<Favorite> findByCustomerIdAndProductId(UUID customerId, UUID productId);

    @Query(nativeQuery = true, value = "DELETE FROM favorites WHERE customer_id =:customerId " +
            "AND product_id = :productId")
    void deleteByCustomerIdAndProductId(UUID customerId, UUID productId);
}
