package com.treative.flight.service;

import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.components.mapper.ReservationMapper;
import com.treative.flight.components.model.Flight;
import com.treative.flight.components.model.Reservation;
import com.treative.flight.components.model.Tourist;
import com.treative.flight.repository.FlightRepository;
import com.treative.flight.repository.ReservationRepository;
import com.treative.flight.repository.TouristRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ReservationService {

    private TouristRepository touristRepository;
    private FlightRepository flightRepository;
    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;

    public ReservationService(TouristRepository touristRepository, FlightRepository flightRepository,
                              ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.touristRepository = touristRepository;
        this.flightRepository = flightRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public Optional<ReservationDto> findTouristById(Long id) {
        return reservationRepository.findById(id).map(reservationMapper::toDto);
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Optional<Flight> flight = flightRepository.findById(reservationDto.getFlightId());
        Optional<Tourist> tourist = touristRepository.findById(reservationDto.getTouristId());
        Reservation reservation = new Reservation();

        if (flight.isPresent()) {
            if (checkFreeSeats(flight.get().getSeats())) {
                reservation.setFlight(flight.get());
                reservation.setTourist(tourist.orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tourist has not been found")));
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "There is no free seats in this flight");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight has not been found");
        }

        decreaseFlightSeats(flight.get());
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    public void removeTouristFormFlight(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            Flight flight = reservation.get().getFlight();
            increaseFlightSeats(flight);
        }
    }

    public boolean checkFreeSeats(int seats) {
        if (seats >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public void decreaseFlightSeats(Flight flight) {
        flight.setSeats(flight.getSeats() - 1);
        flightRepository.save(flight);
    }

    public void increaseFlightSeats(Flight flight) {
        flight.setSeats(flight.getSeats() + 1);
        flightRepository.save(flight);
    }

}
