package com.treative.flight.components.mapper;

import com.treative.flight.UnitTestUtil;
import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.components.model.Gender;
import com.treative.flight.components.model.Tourist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class TouristMapperUnitTest {

    private TouristMapper touristMapper;

    @BeforeEach
    void setUp() {
        touristMapper = new TouristMapper();
    }

    @Test
    void shouldMapToDto() {
        //given
        Tourist tourist = UnitTestUtil.createTourist();
        TouristDto touristDto = UnitTestUtil.createTouristDto();

        //when
        TouristDto mappedTourist = touristMapper.toDto(tourist);

        //then
        assertThat(mappedTourist.getId(), equalTo(tourist.getId()));
        assertThat(mappedTourist.getFirstName(), equalTo(tourist.getFirstName()));
        assertThat(mappedTourist.getLastName(), equalTo(tourist.getLastName()));

    }

    @Test
    void shouldMapToEntity() {
        //given
        Tourist tourist = UnitTestUtil.createTourist();
        TouristDto touristDto = UnitTestUtil.createTouristDto();

        //when
        Tourist mappedTourist = touristMapper.toEntity(touristDto);

        //then
        assertThat(mappedTourist.getId(), equalTo(touristDto.getId()));
        assertThat(mappedTourist.getFirstName(), equalTo(touristDto.getFirstName()));
        assertThat(mappedTourist.getLastName(), equalTo(touristDto.getLastName()));
    }

}
