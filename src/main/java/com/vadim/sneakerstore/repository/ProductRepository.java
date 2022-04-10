package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);

//    @Query(nativeQuery = true, value = "SELECT * FROM products " +
//            "JOIN products_sizes ON products.id = products_sizes.product_id " +
//            "JOIN sizes ON products_sizes.size_id = sizes.id " +
//            "WHERE sizes.id IN :sizesIds")
//    List<Product> findAllBySizesIds(List<UUID> sizesIds);

//    List<Product> findAllByIdIn(List<UUID> ids);

    //Page<Product>

    Optional<Product> findByName(String name);
}
