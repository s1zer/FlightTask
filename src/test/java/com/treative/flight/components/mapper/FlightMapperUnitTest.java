package com.treative.flight.components.mapper;

import com.treative.flight.UnitTestUtil;
import com.treative.flight.components.dto.FlightDto;
import com.treative.flight.components.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FlightMapperUnitTest {

    private FlightMapper flightMapper;

    @BeforeEach
    void setUp() {
        flightMapper = new FlightMapper();
    }

    @Test
    void shouldMapToDto() {
        //given
        Flight flight = UnitTestUtil.createFlight();
        FlightDto flightDto = UnitTestUtil.createFlightDto();

        //when
        FlightDto mappedFlight = flightMapper.toDto(flight);

        //then
        assertThat(mappedFlight.getId(), equalTo(flight.getId()));
        assertThat(mappedFlight.getArrival(), equalTo(flight.getArrival()));
        assertThat(mappedFlight.getDeparture(), equalTo(flight.getDeparture()));
    }

    @Test
    void shouldMapToEntity() {
        //given
        Flight flight = UnitTestUtil.createFlight();
        FlightDto flightDto = UnitTestUtil.createFlightDto();

        //when
        Flight mappedFlight = flightMapper.toEntity(flightDto);

        //then
        assertThat(mappedFlight.getId(), equalTo(flightDto.getId()));
        assertThat(mappedFlight.getArrival(), equalTo(flightDto.getArrival()));
        assertThat(mappedFlight.getDeparture(), equalTo(flightDto.getDeparture()));
    }

}
