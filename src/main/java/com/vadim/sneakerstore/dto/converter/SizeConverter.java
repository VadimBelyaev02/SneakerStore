package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.entity.Size;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SizeConverter {

    public SizeDto convertToDto(Size size) {
        UUID id = size.getId();
        Integer productSize = size.getSize();
        Integer amount = size.getAmount();

        List<UUID> productIds = new ArrayList<>();

        if (Objects.nonNull(size.getProducts())) {
            productIds = size.getProducts().stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
        }

        return SizeDto.builder()
                .id(id)
                .productIds(productIds)
                .size(productSize)
                .amount(amount)
                .build();
    }

    public Size convertToEntity(SizeDto sizeDto) {
        UUID id = sizeDto.getId();
        Integer productSize = sizeDto.getSize();
        Integer amount = sizeDto.getAmount();

        return Size.builder()
                .id(id)
                .size(productSize)
                .amount(amount)
                .build();
    }
}
