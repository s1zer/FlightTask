package com.treative.flight.controller.rest;

import com.treative.flight.FlightTaskConstants;
import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/tourists")
public class TouristRestController {

    private TouristService touristService;

    public TouristRestController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("")
    public List<TouristDto> getAllTourists() {
        return touristService.findAllTourists();
    }

    @PostMapping("")
    ResponseEntity<TouristDto> saveTourist(@RequestBody TouristDto touristDto) {
        if (touristDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FlightTaskConstants.ID_MUST_BE_EMPTY_MESSAGE);
        } else {
            return ResponseEntity.ok(touristService.save(touristDto));
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteTourist(@PathVariable Long id) {
        if (touristService.findTouristById(id).isPresent()) {
            touristService.deleteTouristById(id);
            return new ResponseEntity<String>(FlightTaskConstants.SUCCESS_MESSAGE, HttpStatus.ACCEPTED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FlightTaskConstants.TOURIST_NOT_FOUND_MESSAGE);
        }
    }

}
