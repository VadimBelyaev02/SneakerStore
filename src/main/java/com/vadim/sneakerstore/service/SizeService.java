package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.entity.Size;

import java.util.List;
import java.util.UUID;

public interface SizeService {

    SizeDto getById(UUID id);

    List<SizeDto> getAll();

    SizeDto save(SizeDto sizeDto);

    SizeDto update(SizeDto sizeDto);

    void deleteById(UUID id);

    List<SizeDto> getAllByProductId(UUID productId);

    List<SizeDto> saveAll(List<SizeDto> sizeDtos);

    List<SizeDto> getAllByProductsIds(List<UUID> ids);
}
