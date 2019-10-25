package com.treative.flight.controller.rest;

import com.treative.flight.components.dto.FlightDto;
import com.treative.flight.components.model.Flight;
import com.treative.flight.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FlightRestControllerUnitTest {

    private FlightRestController flightRestController;
    @Mock
    private FlightService flightService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        flightRestController = new FlightRestController(flightService);
        mockMvc = MockMvcBuilders.standaloneSetup(flightRestController).build();
    }

    @Test
    void shouldGetAllFlights() throws Exception {
        //given
        given(flightService.findAllFlights()).willReturn(getFlightsDto());

        //when
        flightRestController.getAllFlights();

        //then
        verify(flightService, times(1)).findAllFlights();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void exceptionShouldBeThrownWhenIdNotEmpty() {
        //given
        FlightDto flightToSave = getFlightDto();
        flightToSave.setId(10L);

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> flightRestController.saveFlight(flightToSave));
    }

    @Test
    void flightShouldBeSaved() throws Exception {
        //given
        FlightDto flightToSave = getFlightDto();
        flightToSave.setId(null);

        //when
        flightRestController.saveFlight(flightToSave);

        //then
        verify(flightService, times(1)).saveFlight(any(FlightDto.class));
    }

    @Test
    void flightShouldNotBeRemoved() {
        //given
        FlightDto flightToRemove = getFlightDto();
        given(flightService.findFlightById(anyLong())).willReturn(Optional.empty());

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> flightRestController.deleteFlight(anyLong()));
    }

    @Test
    void flightShouldBeRemoved() throws Exception {
        //given
        FlightDto flightToRemove = getFlightDto();
        given(flightService.findFlightById(anyLong())).willReturn(Optional.of(getFlightDto()));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/flights/1"))
                .andExpect(status().isAccepted());
    }

    private Flight getFlight() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setId(1L);
        flight.setArrival(LocalDateTime.of(2019, 01, 01, 20, 20, 20));
        flight.setDeparture(LocalDateTime.of(2019, 01, 01, 22, 20, 20));
        flight.setSeats(200);
        flight.setTicketPrice(350);
        return flight;
    }

    private FlightDto getFlightDto() {
        FlightDto flightDto = new FlightDto();
        flightDto.setId(1L);
        flightDto.setArrival(LocalDateTime.of(2019, 01, 01, 20, 20, 20));
        flightDto.setDeparture(LocalDateTime.of(2019, 01, 01, 22, 20, 20));
        flightDto.setSeats(200);
        flightDto.setTicketPrice(350);
        return flightDto;
    }

    List<FlightDto> getFlightsDto() {
        return Arrays.asList(getFlightDto());
    }
}
