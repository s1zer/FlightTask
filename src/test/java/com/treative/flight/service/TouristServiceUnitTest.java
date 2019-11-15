package com.treative.flight.service;

import com.treative.flight.UnitTestUtil;
import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.components.mapper.TouristMapper;
import com.treative.flight.components.model.Gender;
import com.treative.flight.components.model.Tourist;
import com.treative.flight.repository.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TouristServiceUnitTest {

    private TouristRepository touristMockRepository;
    private TouristService touristService;
    private TouristMapper touristMapper;

    @BeforeEach
    void setUp() {
        touristMapper = new TouristMapper();
        touristMockRepository = mock(TouristRepository.class);
        touristService = new TouristService(touristMockRepository, touristMapper);
    }

    @Test
    void shouldGetAllTourists() {
        //given
        List<Tourist> tourists = UnitTestUtil.createTouristsList();
        given(touristMockRepository.findAll()).willReturn(tourists);

        //when
        List<TouristDto> allTourists = touristService.findAllTourists();

        //then
        verify(touristMockRepository, times(1)).findAll();
        assertThat(allTourists, hasSize(2));
    }

    @Test
    void shouldGetTouristById() {
        //given
        Tourist tourist = UnitTestUtil.createTourist();
        given(touristMockRepository.findById(anyLong())).willReturn(Optional.of(tourist));

        //when
        Optional<TouristDto> foundTourist = touristService.findTouristById(anyLong());

        //then
        verify(touristMockRepository, times(1)).findById(anyLong());
        assertThat(foundTourist.get().getId(), equalTo(tourist.getId()));
    }

    @Test
    void shouldSaveNewTourist() {
        //given
        TouristDto touristToSave = UnitTestUtil.createTouristDto();
        given(touristMockRepository.save(any(Tourist.class))).willReturn(UnitTestUtil.createTourist());

        //when
        TouristDto savedTourist = touristService.save(touristToSave);

        //then
        verify(touristMockRepository, times(1)).save(any(Tourist.class));
        assertThat(savedTourist.getFirstName(), equalTo(touristToSave.getFirstName()));
    }

    @Test
    void touristShouldBeRemoved() {
        //given
        given(touristMockRepository.findById(anyLong())).willReturn(Optional.of(UnitTestUtil.createTourist()));

        //when
        touristService.deleteTouristById(anyLong());

        //then
        verify(touristMockRepository, times(1)).delete(any(Tourist.class));
    }

    @Test
    void touristShouldNotBeRemoved() {
        //given
        given(touristMockRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        touristService.deleteTouristById(anyLong());

        //then
        verify(touristMockRepository, times(0)).delete(any(Tourist.class));
    }

}
