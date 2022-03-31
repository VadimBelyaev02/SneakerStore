package com.vadim.sneakerstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDto {

    private UUID id;

    @NotNull
    private Integer amount;

    @NotNull
    private Integer size;

    @NotNull
    private UUID productId;
}
