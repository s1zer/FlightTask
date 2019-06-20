package com.treative.flight.components.mapper;

import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.components.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {

    public ReservationDto toDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setTouristId(reservation.getTourist().getId());
        reservationDto.setFlightId(reservation.getFlight().getId());

        return reservationDto;
    }
    
}
