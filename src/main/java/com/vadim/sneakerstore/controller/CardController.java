package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.service.CardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "Card Controller", description = "Card CRUD operations")
@RestController
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto postCard(@Valid @RequestBody CardDto cardDto) {
        return cardService.saveCard(cardDto);
    }

    @DeleteMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable("id") UUID id) {
        cardService.deleteCard(id);
    }
}
