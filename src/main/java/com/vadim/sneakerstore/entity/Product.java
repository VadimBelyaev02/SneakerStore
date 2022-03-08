package com.vadim.sneakerstore.entity;

import com.vadim.sneakerstore.entity.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
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
    private BigDecimal price;

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

    @ManyToMany
    @JoinTable(name = "products_sizes",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "size_id", referencedColumnName = "id"))
    private List<Size> sizes;

    @Column(name = "preview")
    private String preview;

    @OneToMany
    private List<Photo> photos;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Customer> customers;

    @OneToMany
    private List<Comment> comments;

}
