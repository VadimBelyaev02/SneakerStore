package com.vadim.sneakerstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.vadim.sneakerstore.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(name = "Card", description = "Transfer card data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private UUID id;

    @Schema(description = "Card number")
    @NotBlank
    private String number;

    @Schema(description = "Validity date")
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate validityDate;

    @Schema(description = "Owner on a card")
    @NotBlank
    private String owner;

    @Schema(description = "Cvv number")
    @NotNull
    private String cvv;

    @Schema(description = "Ids of people that have this card in their accounts")
    private List<UUID> customersIds;
}
