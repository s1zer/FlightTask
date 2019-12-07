package com.treative.flight.service;

import com.treative.flight.UnitTestUtil;
import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.components.mapper.ReservationMapper;
import com.treative.flight.components.model.Flight;
import com.treative.flight.components.model.Reservation;
import com.treative.flight.components.model.Tourist;
import com.treative.flight.repository.FlightRepository;
import com.treative.flight.repository.ReservationRepository;
import com.treative.flight.repository.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ReservationServiceUnitTest {

    private TouristRepository touristMockRepository;
    private FlightRepository flightMockRepository;
    private ReservationRepository reservationMockRepository;
    private ReservationMapper reservationMapper;
    private ReservationService reservationService;
    private ReservationService reservationMockService;
    private FlightService flightMockService;

    @BeforeEach
    void setUp() {
        reservationMapper = new ReservationMapper();
        flightMockRepository = mock(FlightRepository.class);
        reservationMockRepository = mock(ReservationRepository.class);
        touristMockRepository = mock(TouristRepository.class);
        flightMockService = mock(FlightService.class);

        reservationService = new ReservationService(touristMockRepository,
                flightMockRepository,
                reservationMockRepository,
                reservationMapper, flightMockService);
        reservationMockService = mock(ReservationService.class);
    }

    @Test
    void shouldDecreaseFlightSeats() {
        //given
        Flight flight = UnitTestUtil.createFlight();
        flight.setSeats(10);

        //when
        reservationService.decreaseFlightSeats(flight);

        //then
        verify(flightMockRepository, times(1)).save(any(Flight.class));
        assertThat(flight.getSeats(), equalTo(9));
    }

    @Test
    void shouldIncreaseFlightSeats() {
        //given
        Flight flight = UnitTestUtil.createFlight();
        flight.setSeats(10);

        //when
        reservationService.increaseFlightSeats(flight);

        //then
        verify(flightMockRepository, times(1)).save(any(Flight.class));
        assertThat(flight.getSeats(), equalTo(11));
    }

    @Test
    void shouldCreateNewReservation() {
        //given
        Flight flight = UnitTestUtil.createFlight();
        Tourist tourist = new Tourist();
        ReservationDto reservationDto = UnitTestUtil.createReservationDto();

        given(touristMockRepository.findById(anyLong())).willReturn(Optional.of(tourist));
        given(flightMockRepository.findById(anyLong())).willReturn(Optional.of(flight));
        given(reservationMockRepository.save(any(Reservation.class))).willReturn(UnitTestUtil.createReservation());

        //when
        reservationService.createReservation(reservationDto);

        //then
        verify(reservationMockRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void exceptionShouldBeThrownWhenFlightNotFound() {
        //given
        Flight flight = UnitTestUtil.createFlight();
        Tourist tourist = new Tourist();
        ReservationDto reservationDto = UnitTestUtil.createReservationDto();

        given(touristMockRepository.findById(anyLong())).willReturn(Optional.of(tourist));
        given(flightMockRepository.findById(anyLong())).willReturn(Optional.empty());
        given(reservationMockRepository.save(any(Reservation.class))).willReturn(UnitTestUtil.createReservation());

        //when
        //then
        verify(reservationMockRepository, times(0)).save(any(Reservation.class));
        assertThrows(ResponseStatusException.class, () -> reservationService.createReservation(reservationDto));
    }

    @Test
    void exceptionShouldBeThrownWhenTouristNotFound() {
        //given
        Flight flight = UnitTestUtil.createFlight();
        Tourist tourist = new Tourist();
        ReservationDto reservationDto = UnitTestUtil.createReservationDto();

        given(touristMockRepository.findById(anyLong())).willReturn(Optional.empty());
        given(flightMockRepository.findById(anyLong())).willReturn(Optional.of(flight));
        given(reservationMockRepository.save(any(Reservation.class))).willReturn(UnitTestUtil.createReservation());

        //when
        //then
        verify(reservationMockRepository, times(0)).save(any(Reservation.class));
        assertThrows(ResponseStatusException.class, () -> reservationService.createReservation(reservationDto));
    }

    @Test
    void exceptionShouldBeThrownWhenNotEnoughSeatsInFlight() {
        //given
        Flight flight = UnitTestUtil.createFlight();
        flight.setSeats(0);
        Tourist tourist = new Tourist();
        ReservationDto reservationDto = UnitTestUtil.createReservationDto();

        given(touristMockRepository.findById(anyLong())).willReturn(Optional.of(tourist));
        given(flightMockRepository.findById(anyLong())).willReturn(Optional.of(flight));
        given(reservationMockRepository.save(any(Reservation.class))).willReturn(UnitTestUtil.createReservation());

        //when
        //then
        verify(reservationMockRepository, times(0)).save(any(Reservation.class));
        assertThrows(ResponseStatusException.class, () -> reservationService.createReservation(reservationDto));
    }

    @Test
    void reservationShouldBeRemoved() {
        //given
        Reservation reservation = UnitTestUtil.createReservation();
        given(reservationMockRepository.findById(anyLong())).willReturn(Optional.of(reservation));

        //when
        reservationService.removeTouristFormFlight(anyLong());

        //then
        verify(reservationMockRepository, times(1)).delete(any(Reservation.class));
    }

    @Test
    void reservationShouldNotBeRemoved() {
        //given
        Reservation reservation = UnitTestUtil.createReservation();
        given(reservationMockRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        reservationService.removeTouristFormFlight(anyLong());

        //then
        verify(reservationMockRepository, times(0)).delete(any(Reservation.class));
    }

}
