package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Favorite;
import com.vadim.sneakerstore.entity.ProductCustomerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, ProductCustomerId> {

    List<Favorite> findAllByCustomerId(UUID customerId);

   // boolean existsByCustomerIdAndProductId();

    boolean existsByCustomerIdAndProductId(UUID customerId, UUID productId);

    //   Favorite findByCustomerIdAndProductId();

    Optional<Favorite> findByCustomerIdAndProductId(UUID customerId, UUID productId);

    void deleteByCustomerIdAndProductId(UUID customerId, UUID productId);
}
