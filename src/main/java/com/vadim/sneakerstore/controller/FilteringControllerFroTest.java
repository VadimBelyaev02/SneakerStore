package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Comment Controller", description = "Comment CRUD operations")
@RestController
@RequestMapping("/api")
public class FilteringControllerFroTest {

//    private final ProductService service;
//
//    public List<ProductDto> getAll
}
