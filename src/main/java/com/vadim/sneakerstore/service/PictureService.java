package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.PictureDto;

import java.util.List;
import java.util.UUID;

public interface PictureService {

    PictureDto getById(String id);

    List<PictureDto> getAll();

    PictureDto save(PictureDto pictureDto);

    PictureDto update(PictureDto pictureDto);

    void deleteById(String id);
}
