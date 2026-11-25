package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.dto.CreateFacilityCommand;
import com.miszunXD.medicalclinic.dto.FacilityDto;
import com.miszunXD.medicalclinic.exception.FacilityAlreadyExistsException;
import com.miszunXD.medicalclinic.exception.FacilityNotFoundException;
import com.miszunXD.medicalclinic.mapper.FacilityMapper;
import com.miszunXD.medicalclinic.model.Facility;
import com.miszunXD.medicalclinic.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityMapper facilityMapper;
    private final FacilityRepository facilityRepository;

    @Transactional(readOnly = true)
    public List<FacilityDto> getFacilities() {
        log.info("Start process opf getting facilities");
        List<FacilityDto> result = facilityMapper.toDtos(facilityRepository.findAll());
        log.info("List of {} facilities", result.size());
        return result;
    }

    @Transactional
    public FacilityDto addFacility(CreateFacilityCommand command) {
        log.info("Add new facility {}", command);
        validateFacilityNameIsUnique(command.name());
        Facility saved = facilityRepository.save(facilityMapper.toEntity(command));
        log.info("Added new facility, id: {}", saved.getId());
        return facilityMapper.toDto(saved);
    }

    public FacilityDto getFacilityById(Long id) {
        log.info("GET facility by id: {}", id);
        return facilityMapper.toDto(findFacilityById(id));
    }

    private void validateFacilityNameIsUnique(String name) {
        if (facilityRepository.existsByName(name)) {
            throw new FacilityAlreadyExistsException("Facility with given name: " + name + " already exists!");
        }
    }

    private Facility findFacilityById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new FacilityNotFoundException("Facility with given id: " + id + " not found!"));
    }
}
