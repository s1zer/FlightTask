package com.treative.flight.controller.rest;

import com.treative.flight.UnitTestUtil;
import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.components.model.Gender;
import com.treative.flight.components.model.Tourist;
import com.treative.flight.service.TouristService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TouristRestControllerUnitTest {

    private TouristRestController touristRestController;
    @Mock
    private TouristService touristService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        touristRestController = new TouristRestController(touristService);
        mockMvc = MockMvcBuilders.standaloneSetup(touristRestController).build();
    }

    @Test
    void shouldGetAllTourists() throws Exception {
        //given
        given(touristService.findAllTourists()).willReturn(UnitTestUtil.createTouristsDtoList());

        //when
        touristRestController.getAllTourists();

        //then
        verify(touristService, times(1)).findAllTourists();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tourists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void exceptionShouldBeThrowWhenIdNotEmpty() {
        //given
        TouristDto touristToSave = UnitTestUtil.createTouristDto();

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> touristRestController.saveTourist(touristToSave));
    }

    @Test
    void touristShouldBeSaved() throws Exception {
        //given
        TouristDto touristToSave = UnitTestUtil.createTouristDto();
        touristToSave.setId(null);

        //when
        touristRestController.saveTourist(touristToSave);

        //then
        verify(touristService, times(1)).save(any(TouristDto.class));
    }

    @Test
    void touristShouldNotBeRemoved() {
        //given
        TouristDto touristToRemove = UnitTestUtil.createTouristDto();
        given(touristService.findTouristById(anyLong())).willReturn(Optional.empty());

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> touristRestController.deleteTourist(touristToRemove.getId()));
    }

    @Test
    void touristShouldBeRemoved() throws Exception {
        //given
        TouristDto touristToRemove = UnitTestUtil.createTouristDto();
        given(touristService.findTouristById(anyLong())).willReturn(Optional.of(UnitTestUtil.createTouristDto()));

        //when
        touristRestController.deleteTourist(touristToRemove.getId());

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tourists/1"))
                .andExpect(status().isAccepted());
    }

}
