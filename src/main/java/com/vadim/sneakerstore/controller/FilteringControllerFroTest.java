package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Comment Controller", description = "Comment CRUD operations")
@RestController
@RequestMapping("/api")
public class FilteringControllerFroTest {

    private final ProductService service;

    public FilteringControllerFroTest(ProductService service) {
        this.service = service;
    }
}

//    @Operation(description = "Get all products")
//    @ApiResponse(description = "All products are found", responseCode = "200")
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<ProductDto> getAllProducts(@Parameter(description = "Amount of products on one page")
//                                           @RequestParam(value = "size", defaultValue = "21") int size,
//                                           @Parameter(description = "Number of needed page")
//                                           @RequestParam(value = "page", defaultValue = "0") int page,
//                                           @Parameter(description = "Name of field by that it should be sorted")
//                                           @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
//                                           @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
//                                           @RequestParam(value = "material", required = false) String material,
//                                           @RequestParam(value = "originCountry", required = false) String originCountry,
//                                           @RequestParam(value = "color", required = false) String color,
//                                           @RequestParam(value = "destiny", required = false) String destiny,
//                                           @RequestParam(value = "sex", required = false) String sex,
//                                           @RequestParam(value = "season", required = false) String brand,
//                                           @RequestParam(value = "priceFrom", defaultValue = "0") Integer priceFrom,
//                                           @RequestParam(value = "priceTo", required = false) Integer priceTo) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
//        ProductDto productDto = ProductDto.builder()
//                .material(material)
//                .color(color)
//                .build();
//        return service.getSortingAndFiltering(productDto, pageable);
//    }

//    @Operation(description = "Get all products")
//    @ApiResponse(description = "All products are found", responseCode = "200")
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<ProductDto> getAllProducts(@Parameter(description = "Amount of products on one page")
//                                           @RequestParam(value = "size", defaultValue = "21") int size,
//                                           @Parameter(description = "Number of needed page")
//                                           @RequestParam(value = "page", defaultValue = "0") int page,
//                                           @Parameter(description = "Name of field by that it should be sorted")
//                                           @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
//                                           @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
//                                           @RequestParam Map<String, List<String>> fields) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
//        return service.getSortingAndFiltering(fields, pageable);
//}
