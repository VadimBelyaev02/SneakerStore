package com.vadim.sneakerstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "Favorite", description = "Describe a product that a customer added in his favorites")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {

    @Schema(description = "Id of a customer that added this product in his favorites")
    @NotNull
    private UUID customerId;

    @Schema(description = "Product that are in the customer's favorites")
    @NotNull
    private UUID productId;


}
