package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.PhotoDto;
import com.vadim.sneakerstore.dto.converter.PhotoConverter;
import com.vadim.sneakerstore.entity.Picture;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.PhotoRepository;
import com.vadim.sneakerstore.service.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository repository;
    private final PhotoConverter converter;

    public PhotoServiceImpl(PhotoRepository repository, PhotoConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }


    @Override
    @Transactional
    public PhotoDto getById(UUID id) {
        Picture picture = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Photo with id = " + id + " is not found")
        );
        return converter.convertToDto(picture);
    }

    @Override
    @Transactional
    public List<PhotoDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PhotoDto save(PhotoDto photoDto) {
        if (repository.existsById(photoDto.getId())) {
            throw new AlreadyExistsException("Photo with id = " + photoDto.getId() + " already exists");
        }
        Picture picture = repository.save(converter.convertToEntity(photoDto));
        return converter.convertToDto(picture);
    }

    @Override
    @Transactional
    public PhotoDto update(PhotoDto photoDto) {
        if (!repository.existsById(photoDto.getId())) {
            throw new NotFoundException("Photo with id = " + photoDto.getId() + " is not found");
        }
        Picture picture = repository.save(converter.convertToEntity(photoDto));
        return converter.convertToDto(picture);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Photo with id = " + id + " is not found");
        }
        repository.deleteById(id);
    }
}
