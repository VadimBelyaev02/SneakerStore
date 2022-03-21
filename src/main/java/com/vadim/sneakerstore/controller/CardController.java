package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public CardDto postCard(@Parameter(description = "Contains ") @Valid @RequestBody CardDto cardDto) {
        return cardService.saveCard(cardDto);
    }

    @Operation(description = "Delete a card by id")
    @DeleteMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable("id") UUID id) {
        cardService.deleteCard(id);
    }

    @GetMapping("/users/{userId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<CardDto> getCustomerCards(@PathVariable("userId") UUID userId) {
        return cardService.getCardByUserId(userId);
    }
}
