package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @ApiResponse(description = "Product is found", responseCode = "200")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@Parameter(description = "Id of needed product")
                                 @PathVariable("id") UUID id) {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        return productDto;
    }

    @Operation(description = "Get all products")
    @ApiResponse(description = "All products are found", responseCode = "200")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts(@Parameter(description = "Amount of products on one page")
                                           @RequestParam(value = "size", defaultValue = "21") int size,
                                           @Parameter(description = "Number of needed page")
                                           @RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllPaging(pageable);
    }

    @Operation(description = "Add a new product")
    @ApiResponse(description = "Product is successfully created", responseCode = "201")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto postProduct(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about a new product")
                                  @Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @Operation(description = "Update existed product")
    @ApiResponse(description = "Product is successfully updated", responseCode = "200")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto putProduct(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about new product's fields")
                                 @Valid @RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @Operation(description = "Delete product by id")
    @ApiResponse(description = "Product is successfully deleted", responseCode = "204")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@Parameter(description = "Id of deleted product")
                              @PathVariable("id") UUID id) {
        productService.deleteById(id);
    }
}
