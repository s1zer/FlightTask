package com.treative.flight.controller.rest;

import com.treative.flight.UnitTestUtil;
import com.treative.flight.components.dto.ReservationDto;
import com.treative.flight.components.model.Flight;
import com.treative.flight.components.model.Reservation;
import com.treative.flight.components.model.Tourist;
import com.treative.flight.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationRestControllerUnitTest {

    private ReservationRestController reservationRestController;
    @Mock
    private ReservationService reservationService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationRestController = new ReservationRestController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationRestController).build();
    }

    @Test
    void shouldReturnAllReservations() throws Exception {
        //given
        given(reservationService.findAllReservations()).willReturn(UnitTestUtil.createReservationsDtoList());

        //when
        reservationRestController.getAllReservations();

        //then
        verify(reservationService, times(1)).findAllReservations();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void reservationShouldBeSaved() throws Exception {
        //given
        ReservationDto reservationToSave = UnitTestUtil.createReservationDto();
        reservationToSave.setId(null);

        //when
        reservationRestController.saveReservation(reservationToSave);

        //then
        verify(reservationService, times(1)).createReservation(any(ReservationDto.class));
    }

    @Test
    void reservationShouldNotBeSaved() throws Exception {
        //given
        ReservationDto reservationToSave = UnitTestUtil.createReservationDto();
        reservationToSave.setId(1L);

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> reservationRestController.saveReservation(reservationToSave));
    }

    @Test
    void reservationShouldBeRemoved() throws Exception {
        //given
        given(reservationService.findTouristById(anyLong())).willReturn(Optional.of(UnitTestUtil.createReservationDto()));

        //when
        reservationRestController.deleteReservation(1L);

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservations/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    void reservationShouldNotBeRemoved() throws Exception {
        //given
        given(reservationService.findTouristById(anyLong())).willReturn(Optional.empty());

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> reservationRestController.deleteReservation(anyLong()));
    }

}
