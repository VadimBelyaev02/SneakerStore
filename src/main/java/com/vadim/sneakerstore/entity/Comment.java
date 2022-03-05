package com.vadim.sneakerstore.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "message")
    private String message;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "createdDate")
    private LocalDate createdDate;

    @Column(name = "username")
    private String username;

    @ManyToOne
    private Product product;
}