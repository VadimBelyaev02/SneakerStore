package com.vadim.sneakerstore.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sizes")
public class Size {

    @Id
    private UUID id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "size")
    private Integer size;

    @ManyToMany
    private List<Product> products;

}
