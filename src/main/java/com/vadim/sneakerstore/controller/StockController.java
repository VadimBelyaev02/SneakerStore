package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.StockDto;
import com.vadim.sneakerstore.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Stock Controller", description = "Stock CRUD operations")
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @Operation(description = "Get stock by id")
    @ApiResponse(description = "Stock is found", responseCode = "200")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StockDto getById(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all stocks")
    @ApiResponse(description = "All stocks are found", responseCode = "200")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockDto> getAllStocks() {
        return service.getAll();
    }

    @Operation(description = "Add a new stock")
    @ApiResponse(description = "Stock is added", responseCode = "201")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockDto postStocks(@Valid @RequestBody StockDto stockDto) {
        return service.save(stockDto);
    }

    @Operation(description = "Update existed stock")
    @ApiResponse(description = "Stock is successfully updated", responseCode = "200")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StockDto putStocks(@Valid @RequestBody StockDto stockDto) {
        return service.update(stockDto);
    }

    @Operation(description = "Delete stock by id")
    @ApiResponse(description = "Stock is successfully deleted", responseCode = "204")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStocks(@PathVariable("id") UUID id) {
        service.deleteById(id);
    }



}
