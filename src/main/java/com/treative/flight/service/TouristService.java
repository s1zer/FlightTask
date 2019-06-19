package com.treative.flight.service;

import com.treative.flight.components.dto.TouristDto;
import com.treative.flight.components.mapper.TouristMapper;
import com.treative.flight.components.model.Tourist;
import com.treative.flight.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TouristService {

    private TouristRepository touristRepository;
    private TouristMapper touristMapper;

    public TouristService(TouristRepository touristRepository, TouristMapper touristMapper) {
        this.touristRepository = touristRepository;
        this.touristMapper = touristMapper;
    }

    public List<TouristDto> findAllTourists() {
        return touristRepository.findAll()
                .stream()
                .map(touristMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TouristDto> findTouristById(Long id) {
        return touristRepository.findById(id).map(touristMapper::toDto);
    }

    public TouristDto save(TouristDto touristDto) {
        Tourist savedTourist = touristRepository.save(touristMapper.toEntity(touristDto));
        return touristMapper.toDto(savedTourist);
    }

    public void deleteTouristById(Long id) {
        Optional<Tourist> touristToRemove = touristRepository.findById(id);
        touristToRemove.ifPresent(t -> touristRepository.delete(t));
    }
}
