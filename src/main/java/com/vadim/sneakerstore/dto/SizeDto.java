package com.vadim.sneakerstore.dto;

import com.vadim.sneakerstore.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {

    private UUID id;

    private Integer amount;

    private Integer size;

    private List<UUID> productIds;

}
