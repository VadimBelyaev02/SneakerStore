package com.vadim.sneakerstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.vadim.sneakerstore.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private UUID id;

    @NotBlank
    private String number;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate validityDate;

    @NotBlank
    private String owner;

    @NotNull
    private Integer cvv;

    @NotNull
    private UUID customerId;
}
