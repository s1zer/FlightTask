package com.treative.flight;

import com.treative.flight.components.dto.FlightDto;
import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.components.model.Flight;
import com.treative.flight.components.model.Gender;
import com.treative.flight.components.model.Reservation;
import com.treative.flight.components.model.Tourist;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UnitTestUtil {

    public static Flight createFlight() {
        Flight flight = new Flight();
        LocalDateTime departure = LocalDateTime.of(2019, Month.JULY, 29, 19, 30, 40);
        LocalDateTime arrival = LocalDateTime.of(2019, Month.JULY, 29, 21, 30, 40);
        flight.setId(1L);
        flight.setSeats(10);
        flight.setTicketPrice(200);
        flight.setDeparture(departure);
        flight.setArrival(arrival);

        return flight;
    }

    public static FlightDto createFlightDto(){
        FlightDto flight = new FlightDto();
        LocalDateTime departure = LocalDateTime.of(2019, Month.JULY, 29, 19, 30, 40);
        LocalDateTime arrival = LocalDateTime.of(2019, Month.JULY, 29, 21, 30, 40);
        flight.setId(1L);
        flight.setSeats(10);
        flight.setTicketPrice(200);
        flight.setDeparture(departure);
        flight.setArrival(arrival);

        return flight;
    }

    public static List<FlightDto> createFlightsDtoList(){
        return Arrays.asList(createFlightDto());
    }

    public static List<Flight> createFlightsList(){
        return Arrays.asList(createFlight(), createFlight());
    }

    public static Reservation createReservation(){
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setFlight(new Flight());
        reservation.setTourist(new Tourist());

        return  reservation;
    }

    public static ReservationDto createReservationDto(){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTouristId(createTouristDto().getId());
        reservationDto.setFlightId(createFlightDto().getId());
        reservationDto.setId(1L);

        return reservationDto;
    }

    public static List<ReservationDto> createReservationsDtoList(){
        return Arrays.asList(createReservationDto());
    }

    public static Tourist createTourist(){
        Tourist tourist = new Tourist();
        LocalDate birthDate = LocalDate.of(1990, 01, 01);
        tourist.setId(1l);
        tourist.setFirstName("Name");
        tourist.setLastName("Surname");
        tourist.setCountry("Poland");
        tourist.setRemarks("No remarks");
        tourist.setBirthDate("1990-01-01");
        tourist.setGender(Gender.MALE);

        return tourist;
    }

    public static TouristDto createTouristDto(){
        TouristDto tourist = new TouristDto();
        tourist.setId(1l);
        tourist.setFirstName("Name");
        tourist.setLastName("Surname");
        tourist.setCountry("Poland");
        tourist.setRemarks("No remarks");
        tourist.setBirthDate("1990-01-01");
        tourist.setGender(Gender.MALE);

        return tourist;
    }

    public static List<TouristDto> createTouristsDtoList(){
        return Arrays.asList(createTouristDto());
    }

    public static List<Tourist> createTouristsList(){
        return Arrays.asList(createTourist(), createTourist());
    }
}
