package com.treative.flight.controller.rest;

import com.treative.flight.components.dto.FlightDto;
import com.treative.flight.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/flights")
public class FlightRestController {

    private FlightService flightService;

    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("")
    public List<FlightDto> getAllFlights() {
        return flightService.findAllFlights();
    }

    @PostMapping("")
    ResponseEntity<FlightDto> saveFlight(@RequestBody FlightDto flightDto) {
        if (flightDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight's id must be empty");
        } else {
            return ResponseEntity.ok(flightService.saveFlight(flightDto));
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Long> deleteFlight(@PathVariable Long id) {
        if (flightService.findFlightById(id).isPresent()) {
            flightService.deleteFlightById(id);
            return ResponseEntity.ok(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight has not been found");
        }
    }
}
