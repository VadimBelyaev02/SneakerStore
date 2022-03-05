package com.vadim.sneakerstore.dto;

import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.entity.Cart;
import com.vadim.sneakerstore.entity.Order;
import com.vadim.sneakerstore.entity.Product;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class CustomerDto {

    private UUID id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotNull
    private UUID cartId;

//    private List<UUID> orderIds;

 //   private List<UUID> favorites;

//    private List<Card> cards;

}
