package com.treative.flight.components.mapper;

import com.treative.flight.UnitTestUtil;
import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.components.model.Flight;
import com.treative.flight.components.model.Reservation;
import com.treative.flight.components.model.Tourist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ReservationMapperUnitTest {

    private ReservationMapper reservationMapper;

    @BeforeEach
    void setUp() {
        reservationMapper = new ReservationMapper();
    }

    @Test
    void shouldMapToDto() {
        //given
        Reservation reservation = UnitTestUtil.createReservation();

        //when
        ReservationDto mappedReservation = reservationMapper.toDto(reservation);

        //then
        assertThat(mappedReservation.getId(), equalTo(reservation.getId()));
    }
}
