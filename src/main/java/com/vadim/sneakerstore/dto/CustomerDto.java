package com.vadim.sneakerstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Person's first name")
    @NotBlank
    private String firstName;

    @Schema(description = "Person's last name")
    @NotBlank
    private String lastName;

    @Schema(description = "Person's phone number")
    @NotBlank
    private String phone;

    @Schema(description = "Person's email")
    @NotBlank
    private String email;

    @Schema(description = "Person's role")
    @NotBlank
    private String role;

    @Schema(description = "What country person lives")
    private String country;

    @Schema(description = "What city person lives")
    private String city;

    @Schema(description = "Exact address of living")
    @NotBlank
    private String address;

    @Schema(description = "Person's avatar")
    private String avatar;

    @Schema(description = "List of person's cards")
    private List<UUID> cards;

    @Schema(description = "List of person's favorite products")
    private List<ProductDto> favorite;

}
