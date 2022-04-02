package com.vadim.sneakerstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Schema(name = "Customer", description = "Information about service's users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonIgnore
    private String password;

    @Schema(description = "Person's email")
    @Email
    private String email;

    @Schema(description = "Person's role")
    private String role;

    @Schema(description = "Person's avatar")
    private String avatar;

    @Schema(description = "Person's living addresses")
    private List<UUID> addressesIds;

    @Schema(description = "List of person's cards")
    private List<UUID> cardsIds;

    @Schema(description = "Comment's ids that are left by the customer")
    private List<UUID> commentsIds;

    @Schema(description = "Products what customers added in favorites")
    private List<UUID> favoritesIds;

    @Schema(description = "Products ids that are in a customer's cart")
    private List<UUID> inCartIds;

    @Schema(description = "Customer's orders")
    private List<UUID> ordersIds;
}
