package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.SizeDto;

import java.util.List;
import java.util.UUID;

public interface SizeService {

    SizeDto getById(UUID id);

    List<SizeDto> getAll();

    SizeDto save(SizeDto sizeDto);

    SizeDto update(SizeDto sizeDto);

    void deleteById(UUID id);
}
