package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.dto.CreateFacilityCommand;
import com.miszunXD.medicalclinic.dto.FacilityDto;
import com.miszunXD.medicalclinic.service.FacilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping
    public Page<FacilityDto> getFacilities(Pageable pageable) {
        log.info("REQUEST GET /facilities");
        Page<FacilityDto> response = facilityService.getFacilities(pageable);
        log.info("RESPONSE GET /facilities | 200 | pages count={}", response.getTotalPages());
        return response;
    }

    @GetMapping("/{id}")
    public FacilityDto getFacilityById(@PathVariable Long id) {
        log.info("REQUEST GET /facility/{}", id);
        FacilityDto response = facilityService.getFacilityById(id);
        log.info("RESPONSE GET /facility/{} | 200", id);
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityDto addFacility(@RequestBody CreateFacilityCommand command) {
        log.info("REQUEST POST /facilities");
        FacilityDto response = facilityService.addFacility(command);
        log.info("RESPONSE POST /facilities | 201");
        return response;
    }
}
