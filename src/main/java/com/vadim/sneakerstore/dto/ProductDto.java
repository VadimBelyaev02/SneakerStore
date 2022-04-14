package com.vadim.sneakerstore.dto;

import com.vadim.sneakerstore.entity.Stock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(name = "Product", description = "Contains product's info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {

    private UUID id;

    @Schema(description = "Product's costs")
    @NotNull
    private Double price;

    @Schema(description = "Product's name")
    @NotBlank
    private String name;

    @Schema(description = "Product's brand")
    @NotBlank
    private String brand;

    @Schema(description = "For what gender")
    @NotBlank
    private String sex;

    @Schema(description = "What a product is used for")
    @NotBlank
    private String destiny; // :D

    @Schema(description = "Product's color")
    @NotBlank
    private String color;

    @Schema(description = "For what time of year is used")
    @NotBlank
    private String season;

    @Schema(description = "Where was made")
    @NotBlank
    private String originCountry;

    @Schema(description = "Product's description")
    private String description;

    @Schema(description = "What it's made of")
    @NotBlank
    private String material;

    @Schema(description = "Average rate by all comments")
    private Double averageRate;

    @Schema(description = "List of size ids")
    private List<UUID> sizesIds;

    @Schema(description = "List of pictures links")
    private List<String> photos;

    @Schema(description = "List of who liked it")
    private List<UUID> customersIds;

    @Schema(description = "List of product's ids that are in a customer's cart")
    private List<UUID> inCustomersCarts;

    @Schema(description = "List of product's comments ids")
    private List<UUID> commentsIds;

    @Schema(description = "Ordered amount")
    private Integer orderedAmount;
}


