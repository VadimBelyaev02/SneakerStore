package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    boolean existsByNumber(String number);

    List<Card> findAllByCustomerId(UUID customerId);
}
