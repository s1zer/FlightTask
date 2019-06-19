package com.treative.flight.service;

import com.treative.flight.components.dto.FlightDto;
import com.treative.flight.components.mapper.FlightMapper;
import com.treative.flight.components.model.Flight;
import com.treative.flight.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class FlightServiceUnitTest {

    private FlightRepository flightMockRepository;
    private FlightService flightService;
    private FlightMapper flightMapper;

    @BeforeEach
    void setUp() {
        flightMapper = new FlightMapper();
        flightMockRepository = mock(FlightRepository.class);
        flightService = new FlightService(flightMockRepository, flightMapper);
    }

    @Test
    void shouldGetAllFlights() {
        //given
        List<Flight> flights = getAllFlights();
        given(flightMockRepository.findAll()).willReturn(flights);

        //when
        List<FlightDto> allFlights = flightService.findAllFlights();

        //then
        verify(flightMockRepository, times(1)).findAll();
        assertThat(allFlights, hasSize(3));
    }

    @Test
    void shouldGetFlightById() {
        //given
        Flight flight = getFlight();
        given(flightMockRepository.findById(anyLong())).willReturn(Optional.of(flight));

        //when
        Optional<FlightDto> foundFlight = flightService.findFlightById(anyLong());

        //then
        verify(flightMockRepository, times(1)).findById(anyLong());
        assertThat(foundFlight.get().getId(), equalTo(flight.getId()));
    }

    @Test
    void shouldSaveNewFlight() {
        //given
        FlightDto flightToSave = getFlightDto();
        given(flightMockRepository.save(any(Flight.class))).willReturn(getFlight());

        //when
        FlightDto savedFlight = flightService.saveFlight(flightToSave);

        //then
        verify(flightMockRepository, times(1)).save(any(Flight.class));
        assertThat(savedFlight.getId(), equalTo(flightToSave.getId()));
    }

    @Test
    void flightShouldBeRemoved() {
        //given
        given(flightMockRepository.findById(anyLong())).willReturn(Optional.of(getFlight()));

        //when
        flightService.deleteFlightById(anyLong());

        //then
        verify(flightMockRepository, times(1)).delete(any(Flight.class));
    }

    @Test
    void flightShouldnOTBeRemoved() {
        //given
        given(flightMockRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        flightService.deleteFlightById(anyLong());

        //then
        verify(flightMockRepository, times(0)).delete(any(Flight.class));
    }

    private List<Flight> getAllFlights() {
        Flight flight1 = new Flight();
        flight1.setId(1L);

        Flight flight2 = new Flight();
        flight1.setId(2L);

        Flight flight3 = new Flight();
        flight1.setId(3L);

        return Arrays.asList(flight1, flight2, flight3);
    }

    private Flight getFlight() {
        Flight flight = new Flight();
        flight.setId(1L);

        return flight;
    }

    private FlightDto getFlightDto() {
        FlightDto flight = new FlightDto();
        flight.setId(1L);

        return flight;
    }
}

