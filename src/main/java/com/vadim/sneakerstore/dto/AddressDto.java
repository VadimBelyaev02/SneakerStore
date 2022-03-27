package com.vadim.sneakerstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddressDto {

    private UUID id;

    private String city;

    private String country;

    private String street;

    private List<UUID> customersIds;
}
