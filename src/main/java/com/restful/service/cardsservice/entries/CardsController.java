package com.restful.service.cardsservice.entries;

import com.restful.service.cardsservice.model.Card;
import com.restful.service.cardsservice.model.CardsDto;
import com.restful.service.cardsservice.service.CardsService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;


@RestController
@RequestMapping("/cards")
public class CardsController {

    private CardsService cardsService;

    @Autowired
    public void setCardsService(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "View a list of all cards", response = Card.class, responseContainer = "List")
    public ResponseEntity<?> getAllCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable paging = PageRequest.of(page, size);

        try {

            return new ResponseEntity<>(
                    cardsService.getAllCards(paging),
                    HttpStatus.OK
            );

        } catch (Exception e) {

            return errorResponse(e);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "find a card by its id", response = Card.class)
    public ResponseEntity<?> getCard(@PathVariable Long id) {
        try {
            Optional<Card> card = cardsService.getCardById(id);
            if (card.isPresent()) {
                return new ResponseEntity<>(
                        card.get(),
                        HttpStatus.OK
                );
            } else {
                return noCardFoundResponse(id);
            }

        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    @GetMapping("/colour")
    @ApiOperation(value = "Find Cards by colour", response = Card.class, responseContainer = "List")
    public ResponseEntity<?> getCardByColour(@RequestParam String colour, @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);
        try {
            List<Card> cards = cardsService.getCardsByColor(colour, paging);
            if (cards.size() > 0) {
                return new ResponseEntity<>(
                        cards,
                        HttpStatus.OK
                );
            } else {
                return noCardFoundResponse(colour, "colour");
            }
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    @GetMapping("/status")
    @ApiOperation(value = "Find Cards by Status", response = Card.class, responseContainer = "List")
    public ResponseEntity<?> getCardByStatus(@RequestParam String status, @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);

        try {
            List<Card> cards = cardsService.getCardsByStatus(status, paging);
            if (cards.size() > 0) {
                return new ResponseEntity<>(
                        cards,
                        HttpStatus.OK
                );
            } else {
                return noCardFoundResponse(status, "status");
            }
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    @GetMapping("/dates")
    @ApiOperation(value = "Find Cards by Date", response = Card.class, responseContainer = "List")
    public ResponseEntity<?> getCardByDate(@RequestParam String date, @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);

        try {
            List<Card> cards = cardsService.getCardsByDate(date, paging);
            if (cards.size() > 0) {
                return new ResponseEntity<>(
                        cards,
                        HttpStatus.OK
                );
            } else {
                return noCardFoundResponse(date, "date");
            }
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    @GetMapping("/names")
    @ApiOperation(value = "Find Cards by Name", response = Card.class, responseContainer = "List")
    public ResponseEntity<?> getCardByName(@RequestParam String name, @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);
        try {
            List<Card> cards = cardsService.getCardsByName(name, paging);
            if (cards.size() > 0) {
                return new ResponseEntity<>(
                        cards,
                        HttpStatus.OK
                );
            } else {
                return noCardFoundResponse(name, "name");
            }

        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    @PostMapping("/save")
    @ApiOperation(value = "Save a new Card", response = Card.class)
    public ResponseEntity<?> createCard(@RequestBody @Valid CardsDto cardsDto) {
        try {
            return new ResponseEntity<>(
                    cardsService.saveNewCards(cardsDto),
                    HttpStatus.CREATED
            );

        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update card with sspecific id", response = Card.class)
    public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody CardsDto cardsDto) {
        try {
            Optional<Card> card = cardsService.getCardById(id);
            if (card.isPresent()) {
                var savedCard = cardsService.updateCard(card.get(), cardsDto);
                if (isNull(savedCard)) {
                    return new ResponseEntity<>(
                            cardsService.updateCard(card.get(), cardsDto),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            null,
                            HttpStatus.FORBIDDEN
                    );
                }
            } else {
                return noCardFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Card with id", response = String.class)
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        try {
            Optional<Card> card = cardsService.getCardById(id);
            if (card.isPresent()) {
                cardsService.deleteCard(card.get());
                return new ResponseEntity<>(
                        String.format("Card with id: %d was deleted", id),
                        HttpStatus.OK
                );
            } else {
                return noCardFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    private ResponseEntity<String> errorResponse(Exception e) {
        return new ResponseEntity<>("Something went wrong :" + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noCardFoundResponse(Long id) {
        return new ResponseEntity<>("No Card found with id: " + id, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> noCardFoundResponse(String someText, String operation) {
        return new ResponseEntity<>("No Card found with " + operation + " : " + someText, HttpStatus.NOT_FOUND);
    }

}
