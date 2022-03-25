package com.vadim.sneakerstore.dto;

import com.vadim.sneakerstore.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Schema(name = "Size", description = "Product's size and amount data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {

    @NotNull
    private UUID id;

    @Schema(description = "Amount of products with this size")
    @NotNull
    private Integer amount;

    @Schema(description = "Product's size")
    @NotNull
    private Integer size;

    @Schema(description = "Id of product that has this amount of the size")
    private UUID productId;
}
