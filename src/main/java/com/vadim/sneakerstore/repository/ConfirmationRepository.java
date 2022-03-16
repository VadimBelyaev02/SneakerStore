package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfirmationRepository extends JpaRepository<Confirmation, UUID> {


}
