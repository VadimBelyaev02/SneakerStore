package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.dto.SortingProduct;
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
import java.util.Map;
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

        return productService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> postAll(@RequestBody List<ProductDto> productDtos) {
        return productService.saveAll(productDtos);
    }

    @Operation(description = "Get products by array of its ids")
    @ApiResponse(description = "All products are found by ids", responseCode = "200")
    @GetMapping("/ids")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProductsByIds(@RequestBody List<UUID> ids) {
        return productService.getAllByIds(ids);
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
