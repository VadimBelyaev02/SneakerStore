package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    boolean existsByCityAndCountryAndStreet(String city, String country, String street);
}
