package com.vadim.sneakerstore.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "number")
    private String number;

    @Column(name = "validity_date")
    private LocalDate validityDate;

    @Column(name = "owner")
    private String owner;

    @Column(name = "cvv")
    private Integer cvv;

    @ManyToOne
    private Customer customer;

}
