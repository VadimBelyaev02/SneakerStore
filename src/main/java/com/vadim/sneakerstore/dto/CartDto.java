package com.vadim.sneakerstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "Cart", description = "Describe product that are in person's cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartDto {

    @Schema(description = "Id of a customer that has this product in his cart")
    @NotNull
    private UUID customerId;

    @Schema(description = "Id of a product that customer has in his cart")
    @NotNull
    private UUID productId;
}
