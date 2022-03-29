package com.vadim.sneakerstore.repository;

import com.vadim.sneakerstore.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    boolean existsByCityAndCountryAndStreet(String city, String country, String street);

    @Query(nativeQuery = true, value = "SELECT * FROM addresses " +
            "JOIN addresses_customers ac on addresses.id = ac.address_id " +
            "WHERE customer_id =:customerId")
    List<Address> findAllByCustomerId(UUID customerId);
}
