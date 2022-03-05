package com.vadim.sneakerstore.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private String role;

//    private List<UUID> orderIds;

 //   private List<UUID> favorites;

//    private List<Card> cards;

}
