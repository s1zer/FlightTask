package com.treative.flight.service;

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
        List<Tourist> tourists = getAllTourists();
        given(touristMockRepository.findAll()).willReturn(tourists);

        //when
        List<TouristDto> allTourists = touristService.findAllTourists();

        //then
        verify(touristMockRepository, times(1)).findAll();
        assertThat(allTourists, hasSize(3));
    }

    @Test
    void shouldGetTouristById() {
        //given
        Tourist tourist = getTourist();
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
        TouristDto touristToSave = getTouristDto();
        given(touristMockRepository.save(any(Tourist.class))).willReturn(getTourist());

        //when
        TouristDto savedTourist = touristService.save(touristToSave);

        //then
        verify(touristMockRepository, times(1)).save(any(Tourist.class));
        assertThat(savedTourist.getFirstName(), equalTo(touristToSave.getFirstName()));
    }

    @Test
    void touristShouldBeRemoved() {
        //given
        given(touristMockRepository.findById(anyLong())).willReturn(Optional.of(getTourist()));

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


    private List<Tourist> getAllTourists() {
        Tourist tourist1 = new Tourist();
        tourist1.setId(1L);
        tourist1.setFirstName("Joseph");
        tourist1.setLastName("Smart");
        tourist1.setGender(Gender.MALE);
        tourist1.setRemarks("No remarks");
        tourist1.setCountry("Norway");
        tourist1.setBirthDate("1985-04-09");

        Tourist tourist2 = new Tourist();
        tourist2.setId(2L);
        tourist2.setFirstName("Anna");
        tourist2.setLastName("Dziuba");
        tourist2.setGender(Gender.FEMALE);
        tourist2.setRemarks("No remarks");
        tourist2.setCountry("Poland");
        tourist2.setBirthDate("1990-02-12");

        Tourist tourist3 = new Tourist();
        tourist3.setId(3L);
        tourist3.setFirstName("Michael");
        tourist3.setLastName("Roster");
        tourist3.setGender(Gender.MALE);
        tourist3.setRemarks("No remarks");
        tourist3.setCountry("Germany");
        tourist3.setBirthDate("1977-11-27");

        return Arrays.asList(tourist1, tourist2, tourist3);
    }

    private TouristDto getTouristDto() {
        TouristDto touristDto = new TouristDto();
        touristDto.setFirstName("Example");
        return touristDto;
    }

    private Tourist getTourist() {
        Tourist tourist = new Tourist();
        tourist.setFirstName("Example");
        return tourist;
    }

}
