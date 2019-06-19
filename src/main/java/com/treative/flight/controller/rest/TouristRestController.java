package com.treative.flight.controller.rest;

import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/tourist")
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tourist's id must be empty");
        } else {
            return ResponseEntity.ok(touristService.save(touristDto));
        }
    }


}