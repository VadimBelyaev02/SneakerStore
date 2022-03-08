package com.vadim.sneakerstore.dto;

import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Photo;
import com.vadim.sneakerstore.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
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
    private String destiny; // :D

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

    private List<UUID> sizeIds;

    @NotBlank
    private String preview;

    private List<UUID> photoIds;

    private List<UUID> customerIds;

    private List<UUID> commentIds;

}
