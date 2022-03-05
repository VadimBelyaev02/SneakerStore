package com.vadim.sneakerstore.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToOne
    private Customer customer;

    @OneToMany
    private List<Product> products;
}
