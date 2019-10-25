package com.treative.flight.controller.rest;

import com.sun.net.httpserver.Authenticator;
import com.treative.flight.components.dto.FlightDto;
import com.treative.flight.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.ldap.Control;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ControllerConstants.ID_MUST_BE_EMPTY_MESSAGE);
        } else {
            return ResponseEntity.ok(flightService.saveFlight(flightDto));
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        if (flightService.findFlightById(id).isPresent()) {
            flightService.deleteFlightById(id);
            return new ResponseEntity<String>(ControllerConstants.SUCCESS_MESSAGE, HttpStatus.ACCEPTED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ControllerConstants.ID_MUST_BE_EMPTY_MESSAGE);
        }
    }
}
