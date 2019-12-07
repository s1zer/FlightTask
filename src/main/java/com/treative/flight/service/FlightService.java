package com.treative.flight.service;

import com.treative.flight.FlightTaskConstants;
import com.treative.flight.components.dto.FlightDto;
import com.treative.flight.components.mapper.FlightMapper;
import com.treative.flight.components.model.Flight;
import com.treative.flight.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private FlightRepository flightRepository;
    private FlightMapper flightMapper;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    public List<FlightDto> findAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<FlightDto> findFlightById(Long id) {
        return flightRepository.findById(id)
                .map(flightMapper::toDto);
    }

    public FlightDto saveFlight(FlightDto flightDto) {
        Flight savedFlight = flightRepository.save(flightMapper.toEntity(flightDto));
        return flightMapper.toDto(savedFlight);
    }

    public void deleteFlightById(Long id) {
        Optional<Flight> flightToRemove = flightRepository.findById(id);
        flightToRemove.ifPresent(f -> flightRepository.delete(f));
    }

    public Optional<Flight> getAvailableFlight(Long flightId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Optional<Flight> findFlight = flightRepository.findById(flightId);

        return Optional.ofNullable(
                findFlight.
                        filter(f -> f.getDeparture().isAfter(currentDateTime))
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, FlightTaskConstants.RESERVATION_TIME_HAS_EXPIRED)));

    }

}


