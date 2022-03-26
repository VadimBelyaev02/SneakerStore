package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import liquibase.pro.packaged.O;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Card Controller", description = "Card CRUD operations")
@RestController
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(description = "Add a new card")
    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto postCard(@Parameter(description = "Contains info about card")
                            @Valid @RequestBody CardDto cardDto) {
        return cardService.save(cardDto);
    }

    @Operation(description = "Delete a card by id")
    @DeleteMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@Parameter(description = "Id of deleted card")
                           @PathVariable("id") UUID id) {
        cardService.deleteById(id);
    }

    @Operation(description = "Get customer's cards by his id")
    @GetMapping("/customers/{customerId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<CardDto> getCustomerCards(@Parameter(description = "Id of a customer that has this card")
                                          @PathVariable("customerId") UUID userId) {
        return cardService.getByCustomerId(userId);
    }

    @Operation(description = "Update existed card")
    @PutMapping("/cards")
    @ResponseStatus(HttpStatus.OK)
    public CardDto putCard(@Parameter(description = "Contains info about card")
                           @Valid @RequestBody CardDto cardDto) {
        return cardService.update(cardDto);
    }
}
