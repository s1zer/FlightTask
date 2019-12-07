package com.treative.flight.controller.rest;

import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationRestController {

    private ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("")
    public List<ReservationDto> getAllReservations() {
        return reservationService.findAllReservations();
    }

    @PostMapping("")
    public ResponseEntity<ReservationDto> saveReservation(@RequestBody ReservationDto reservationDto) {
        if (reservationDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ControllerConstants.ID_MUST_BE_EMPTY_MESSAGE);
        } else {
            return ResponseEntity.ok((reservationService.createReservation(reservationDto)));
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        if (reservationService.findTouristById(id).isPresent()) {
            reservationService.removeTouristFormFlight(id);
            return new ResponseEntity<String>(ControllerConstants.SUCCESS_MESSAGE, HttpStatus.ACCEPTED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ControllerConstants.RESERVATION_NOT_FOUND_MESSAGE);
        }
    }
}
