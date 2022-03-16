package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.PhotoDto;

import java.util.List;
import java.util.UUID;

public interface PhotoService {

    PhotoDto getById(UUID id);

    List<PhotoDto> getAll();

    PhotoDto save(PhotoDto photoDto);

    PhotoDto update(PhotoDto photoDto);

    void deleteById(UUID id);
}
