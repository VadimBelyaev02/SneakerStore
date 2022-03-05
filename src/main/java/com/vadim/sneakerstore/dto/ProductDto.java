package com.vadim.sneakerstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {

    private UUID id;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotBlank
    private String sex;

    @NotBlank
    private String destiny;

    @NotBlank
    private String color;

    @NotBlank
    private String season;

    @NotBlank
    private String originCountry;

    @NotBlank
    private String description;

    @NotBlank
    private String material;

//    @NotBlank
//    private String photos;

  //  private Double avg;

    @NotNull
    private Long size;

    // private Integer amount;
}
