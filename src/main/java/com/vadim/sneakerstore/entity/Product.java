package com.vadim.sneakerstore.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "price")
    private Double price;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "sex")
    private String sex;

    @Column(name = "destiny")
    private String destiny; // :D

    @Column(name = "color")
    private String color;

    @Column(name = "season")
    private String season;

    @Column(name = "origin_country")
    private String originCountry;

    @Column(name = "description")
    private String description;

    @Column(name = "material")
    private String material;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Size> sizes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Picture> pictures;

    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.PERSIST})
    @JoinTable(name = "cart",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Customer> inCustomersCarts;

    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.PERSIST})
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Customer> customers;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Stock> stocks;
}
