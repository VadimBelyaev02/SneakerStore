package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.dto.converter.ProductConverter;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.ProductRepository;
import com.vadim.sneakerstore.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductConverter converter;

    @Test
    public void shouldReturnProductById() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        ProductDto productDto = new ProductDto();
        productDto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(product));
        when(converter.convertToDto(product)).thenReturn(productDto);

        assertEquals(service.getById(id), productDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(product);
    }

    @Test
    public void shouldThrowsNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository, only()).findById(id);
        verify(converter, never()).convertToDto(new Product());
    }

    @Test
    public void shouldReturnListOfProductDtos() {
        Product product = new Product();
        ProductDto productDto = new ProductDto();
        List<Product> products = Stream.of(product, product, product)
                .collect(Collectors.toList());
        List<ProductDto> productDtos = Stream.of(productDto, productDto, productDto)
                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(products);
        when(converter.convertToDto(product)).thenReturn(productDto);

        assertEquals(service.getAll(), productDtos);

        verify(repository, only()).findAll();
        verify(converter, times(3)).convertToDto(product);
    }

    @Test
    public void shouldReturnSavedProduct() {
        String name = "name";
        Product product = new Product();
        ProductDto productDto = new ProductDto();
        productDto.setName(name);

        when(repository.save(product)).thenReturn(product);
        when(converter.convertToDto(product)).thenReturn(productDto);
        when(converter.convertToEntity(productDto)).thenReturn(product);
        when(repository.existsByName(name)).thenReturn(false);

        assertEquals(service.save(productDto), productDto);

        verify(repository, times(1)).existsByName(name);
        verify(repository, times(1)).save(product);
        verify(converter, times(1)).convertToDto(product);
        verify(converter, times(1)).convertToEntity(productDto);
    }

    @Test
    public void shouldThrowsAlreadyExistsException() {
        String name = "name";
        ProductDto productDto = new ProductDto();
        Product product = new Product();
        productDto.setName(name);

        when(repository.existsByName(name)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(productDto));

        verify(repository, only()).existsByName(name);
        verify(repository, never()).save(product);
        verify(converter, never()).convertToEntity(productDto);
        verify(converter, never()).convertToDto(product);
    }

    @Test
    public void shouldReturnUpdatedProductDto() {

    }

    @Test
    public void shouldThrowsNotFoundExceptionWhileUpdating() {
        UUID id = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setId(id);

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.update(productDto));

        verify(repository, only()).existsById(id);
        verify(converter, never()).convertToDto(new Product());
        verify(converter, never()).convertToEntity(productDto);
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhileDeleting() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.deleteById(id));

        verify(repository, only()).existsById(id);
    }

}
