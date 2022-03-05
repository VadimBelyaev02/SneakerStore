package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
}
