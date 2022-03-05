package com.vadim.sneakerstore.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "ordered_date")
    private LocalDate orderedDate;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private Customer customer;

    @OneToMany
    private List<Product> products;
}
