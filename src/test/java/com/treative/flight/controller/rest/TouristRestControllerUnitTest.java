package com.treative.flight.controller.rest;

import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.components.model.Gender;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TouristRestControllerUnitTest {

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
        given(touristService.findAllTourists()).willReturn(getTouristsDto());

        //when
        touristRestController.getAllTourists();

        //then
        verify(touristService, times(1)).findAllTourists();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tourist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void exceptionShouldBeThrowWhenIdNotEmpty() {
        //given
        TouristDto touristToSave = getTouristDto();

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> touristRestController.saveTourist(touristToSave));
    }

    @Test
    void touristShouldBeSaved() throws Exception {
        //given
        TouristDto touristToSave = getTouristDto();
        touristToSave.setId(null);

        //when
        touristRestController.saveTourist(touristToSave);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tourist"))
                .andExpect(status().isOk());
    }

    private TouristDto getTouristDto() {
        TouristDto touristDto = new TouristDto();
        touristDto.setId(1L);
        touristDto.setFirstName("Joseph");
        touristDto.setLastName("Smart");
        touristDto.setCountry("Norway");
        touristDto.setBirthDate("1985-04-09");
        touristDto.setGender(Gender.MALE);
        touristDto.setRemarks("No remarks");

        return touristDto;
    }

    private List<TouristDto> getTouristsDto() {
        return Arrays.asList(getTouristDto(), new TouristDto());
    }


}
