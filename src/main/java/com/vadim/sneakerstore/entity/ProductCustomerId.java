package com.vadim.sneakerstore.entity;


import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ProductCustomerId implements Serializable {

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;

    @ManyToOne(targetEntity = Product.class)
    private Product product;

}
