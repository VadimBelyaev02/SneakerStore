package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndEmail(String phone, String email);


}
