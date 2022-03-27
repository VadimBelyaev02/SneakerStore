package com.vadim.sneakerstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Data
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
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    private Product product;

    @Column(name = "payment")
    private String payment;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "group_id")
    private UUID groupId;
}
