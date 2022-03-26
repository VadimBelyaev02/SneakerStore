package com.vadim.sneakerstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(name = "Order", description = "Person's order data")
@Data
@Builder
public class OrderDto {

    private UUID id;

    @Schema(description = "When ordered")
    @NotBlank
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate date;

    @Schema(description = "Order status")
    @NotBlank
    private String status;

    @Schema(description = "Who ordered email")
    @NotBlank
    private String customerEmail;

    @NotBlank
    private String payment;

    @NotNull
    private UUID productId;

    @NotNull
    private Integer amount;

    @NotNull
    private UUID groupId;



//    @Schema(description = "Amount and sizes of ordered products")
//    @NotNull
//    private List<UUID> orderedSizes;
//
//    @Schema(description = "Ids of products that are ordered")
//    private List<UUID> productsIds;




    // private List<UUID> sizesIds;
}
