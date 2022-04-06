package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.PictureDto;
import com.vadim.sneakerstore.dto.converter.PictureConverter;
import com.vadim.sneakerstore.entity.Picture;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.PictureRepository;
import com.vadim.sneakerstore.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository repository;
    private final PictureConverter converter;

    public PictureServiceImpl(PictureRepository repository, PictureConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }


    @Override
    @Transactional
    public PictureDto getById(String id) {
        Picture picture = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Picture with id = " + id + " is not found")
        );
        return converter.convertToDto(picture);
    }

    @Override
    @Transactional
    public List<PictureDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PictureDto save(PictureDto pictureDto) {
        if (repository.existsById(pictureDto.getLink())) {
            throw new AlreadyExistsException("Picture with link = " + pictureDto.getLink() + " already exists");
        }
        Picture picture = repository.save(converter.convertToEntity(pictureDto));
        return converter.convertToDto(picture);
    }

    @Override
    @Transactional
    public PictureDto update(PictureDto pictureDto) {
        if (!repository.existsById(pictureDto.getLink())) {
            throw new NotFoundException("Picture with link = " + pictureDto.getLink() + " is not found");
        }
        Picture picture = repository.save(converter.convertToEntity(pictureDto));
        return converter.convertToDto(picture);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Picture with link = " + id + " is not found");
        }
        repository.deleteById(id);
    }
}
