package com.vadim.sneakerstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {

    @NotNull
    private UUID id;

    @NotNull
    private Integer amount;

    @NotNull
    private Integer size;

    private List<UUID> productIds;
}
