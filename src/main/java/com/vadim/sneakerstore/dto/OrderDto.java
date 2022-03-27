package com.vadim.sneakerstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Order", description = "Person's order data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Schema(description = "Type of payment")
    @NotBlank
    private String payment;

    @Schema(description = "Id of a product that is ordered")
    @NotNull
    private UUID productId;

    @Schema(description = "Amount of the same ordered products")
    @NotNull
    private Integer amount;

    @Schema(description = "Id of group of products that were ordered at one time")
    @NotNull
    private UUID groupId;

}
