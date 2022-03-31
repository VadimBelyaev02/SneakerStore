package com.vadim.sneakerstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "Stock", description = "Order's size and amount data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDto {

    private UUID id;

    @NotNull
    @Schema(description = "Amount of ordered products")
    private Integer amount;

    @NotNull
    @Schema(description = "Ordered size")
    private Integer size;

    @NotNull
    @Schema(description = "Id of a product that is ordered")
    private UUID productId;
}
