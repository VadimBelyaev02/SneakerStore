package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SizeRepository extends JpaRepository<Size, UUID> {

    List<Size> findAllByProductId(UUID productId);
}
