package com.vadim.sneakerstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @EmbeddedId
    private ProductCustomerId id;

    public UUID getCustomerId() {
        return this.id.getCustomer().getId();
    }

    public UUID getProductId() {
        return this.id.getProduct().getId();
    }
}
