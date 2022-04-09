package com.vadim.sneakerstore.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Schema(name = "Address", description = "Info of person's living place")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddressDto {

    private UUID id;

    @Schema(description = "Person's city of living")
    private String city;

    @Schema(description = "Person's country of living")
    private String country;

    @Schema(description = "Person's street of living")
    private String street;

    @Schema(description = "Person's house's number of living")
    private String house;

    @Schema(description = "Person's apartment")
    private String apartment;

    @Schema(description = "People that are living at this address")
    @ArraySchema()
    private List<UUID> customersIds;
}
