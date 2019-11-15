package com.treative.flight.service;

import com.treative.flight.UnitTestUtil;
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
        List<Flight> flights = UnitTestUtil.createFlightsList();
        given(flightMockRepository.findAll()).willReturn(flights);

        //when
        List<FlightDto> allFlights = flightService.findAllFlights();

        //then
        verify(flightMockRepository, times(1)).findAll();
        assertThat(allFlights, hasSize(2));
    }

    @Test
    void shouldGetFlightById() {
        //given
        Flight flight = UnitTestUtil.createFlight();
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
        FlightDto flightToSave = UnitTestUtil.createFlightDto();
        given(flightMockRepository.save(any(Flight.class))).willReturn(UnitTestUtil.createFlight());

        //when
        FlightDto savedFlight = flightService.saveFlight(flightToSave);

        //then
        verify(flightMockRepository, times(1)).save(any(Flight.class));
        assertThat(savedFlight.getId(), equalTo(flightToSave.getId()));
    }

    @Test
    void flightShouldBeRemoved() {
        //given
        given(flightMockRepository.findById(anyLong())).willReturn(Optional.of(UnitTestUtil.createFlight()));

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

}

