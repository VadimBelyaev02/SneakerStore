package com.vadim.sneakerstore.dto;

import lombok.Builder;
import lombok.Data;

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

    @NotBlank
    private String role;

    private String country;

    private String city;

    @NotBlank
    private String address;

    private String avatar;

    private List<CardDto> cards;

    private List<ProductDto> favorite;

}
