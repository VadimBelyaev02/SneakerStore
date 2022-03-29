package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    boolean existsByNumber(String number);

    @Query(nativeQuery = true, value = "SELECT * FROM cards " +
            "JOIN cards_customers cc on cards.id = cc.card_id " +
            "WHERE customer_id = :customerId")
    List<Card> findAllByCustomerId(UUID customerId);
}
