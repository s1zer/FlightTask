package com.treative.flight.controller.rest;

import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/reservations")
public class ReservationRestController {

    private ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity<ReservationDto> saveReservation(@RequestBody ReservationDto reservationDto) {
        if (reservationDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation's id must be empty");
        } else {
            return ResponseEntity.ok(reservationService.createReservation(reservationDto));
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Long> deleteReservation(@PathVariable Long id) {
        if (reservationService.findTouristById(id).isPresent()) {
            reservationService.removeTouristFormFlight(id);
            return ResponseEntity.ok(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation has not been found");
        }
    }
}
