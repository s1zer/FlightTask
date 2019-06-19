package com.treative.flight.components.mapper;

import com.treative.flight.components.dto.FlightDto;
import com.treative.flight.components.model.Flight;
import org.springframework.stereotype.Service;

@Service
public class FlightMapper {

    public FlightDto toDto(Flight flight) {
        FlightDto flightDto = new FlightDto();
        flightDto.setId(flight.getId());
        flightDto.setArrival(flight.getArrival());
        flightDto.setDeparture(flight.getDeparture());
        flightDto.setTicketPrice(flight.getTicketPrice());
        flightDto.setSeats(flight.getSeats());

        return flightDto;
    }

    public Flight toEntity(FlightDto flightDto) {
        Flight flight = new Flight();
        flight.setId(flightDto.getId());
        flight.setArrival(flightDto.getArrival());
        flight.setDeparture(flightDto.getDeparture());
        flight.setTicketPrice(flightDto.getTicketPrice());
        flight.setSeats(flightDto.getSeats());

        return flight;
    }
}
