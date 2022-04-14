package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.dto.converter.SizeConverter;
import com.vadim.sneakerstore.entity.Size;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.SizeRepository;
import com.vadim.sneakerstore.service.SizeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository repository;
    private final SizeConverter converter;

    public SizeServiceImpl(SizeRepository repository, SizeConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public SizeDto getById(UUID id) {
        Size size = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Size with id = " + id + " is not found")
        );
        return converter.convertToDto(size);
    }

    @Override
    @Transactional
    public List<SizeDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SizeDto save(SizeDto sizeDto) {
        if (repository.existsById(sizeDto.getId())) {
            throw new AlreadyExistsException("Size with id = " + sizeDto.getId() + " already exists");
        }
        Size size = repository.save(converter.convertToEntity(sizeDto));
        return converter.convertToDto(size);
    }

    @Override
    @Transactional
    public SizeDto update(SizeDto sizeDto) {
        if (!repository.existsById(sizeDto.getId())) {
            throw new NotFoundException("Size with id = " + sizeDto.getId() + " is not found");
        }
        Size size = repository.save(converter.convertToEntity(sizeDto));
        return converter.convertToDto(size);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Size with id = " + id + " is not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<SizeDto> getAllByProductId(UUID productId) {
        return repository.findAllByProductId(productId).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SizeDto> saveAll(List<SizeDto> sizeDtos) {
        for (SizeDto sizeDto : sizeDtos) {
            sizeDto.setId(UUID.randomUUID());
        }

        return repository.saveAll(sizeDtos.stream()
                        .map(converter::convertToEntity)
                        .collect(Collectors.toList())).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());

    }
}
