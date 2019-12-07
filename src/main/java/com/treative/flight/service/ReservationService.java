package com.treative.flight.service;

import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.components.mapper.ReservationMapper;
import com.treative.flight.components.model.Flight;
import com.treative.flight.components.model.Reservation;
import com.treative.flight.components.model.Tourist;
import com.treative.flight.FlightTaskConstants;
import com.treative.flight.repository.FlightRepository;
import com.treative.flight.repository.ReservationRepository;
import com.treative.flight.repository.TouristRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private TouristRepository touristRepository;
    private FlightRepository flightRepository;
    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private FlightService flightService;

    public ReservationService(TouristRepository touristRepository,
                              FlightRepository flightRepository,
                              ReservationRepository reservationRepository,
                              ReservationMapper reservationMapper,
                              FlightService flightService) {
        this.touristRepository = touristRepository;
        this.flightRepository = flightRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.flightService = flightService;
    }


    public Optional<ReservationDto> findTouristById(Long id) {
        return reservationRepository.findById(id).map(reservationMapper::toDto);
    }

    public List<ReservationDto> findAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {

        Optional<Flight> availableFlight = flightService.getAvailableFlight(reservationDto.getFlightId());
        Optional<Tourist> tourist = touristRepository.findById(reservationDto.getTouristId());
        Reservation reservation = new Reservation();

        availableFlight = availableFlight.filter(f -> f.getSeats() >= 1);

        if (availableFlight.isPresent()) {
            reservation.setFlight(availableFlight.get());
            reservation.setTourist(tourist.orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, FlightTaskConstants.TOURIST_NOT_FOUND_MESSAGE)));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FlightTaskConstants.NO_FREE_SEATS);
        }

        decreaseFlightSeats(availableFlight.get());
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

    public void decreaseFlightSeats(Flight flight) {
        flight.setSeats(flight.getSeats() - 1);
        flightRepository.save(flight);
    }

    public void increaseFlightSeats(Flight flight) {
        flight.setSeats(flight.getSeats() + 1);
        flightRepository.save(flight);
    }

}
