package com.treative.flight.components.mapper;

import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.components.model.Tourist;
import org.springframework.stereotype.Service;

@Service
public class TouristMapper {

    public TouristDto toDto(Tourist tourist) {
        TouristDto touristDto = new TouristDto();
        touristDto.setId(tourist.getId());
        touristDto.setFirstName(tourist.getFirstName());
        touristDto.setLastName(tourist.getLastName());
        touristDto.setGender(tourist.getGender());
        touristDto.setBirthDate(tourist.getBirthDate());
        touristDto.setCountry(tourist.getCountry());
        touristDto.setRemarks(tourist.getRemarks());

        return touristDto;

    }

    public Tourist toEntity(TouristDto touristDto) {
        Tourist tourist = new Tourist();
        tourist.setId(touristDto.getId());
        tourist.setFirstName(touristDto.getFirstName());
        tourist.setLastName(touristDto.getLastName());
        tourist.setGender(touristDto.getGender());
        tourist.setBirthDate(touristDto.getBirthDate());
        tourist.setRemarks(touristDto.getRemarks());
        tourist.setCountry(touristDto.getCountry());

        return tourist;
    }


}
