package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Product Controller", description = "Product CRUD operations")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(description = "Get product by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable("id") UUID id) {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        return productDto;
    }

    @Operation(description = "Get all products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAllTest(@RequestParam(value = "size", defaultValue = "21") int size,
                                        @RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllPaging(pageable);
    }

    @Operation(description = "Add a new product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto postProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @Operation(description = "Update existed product")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto putProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @Operation(description = "Delete product by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteById(id);
    }
}
