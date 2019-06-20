package com.treative.flight.controller.rest;

import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
