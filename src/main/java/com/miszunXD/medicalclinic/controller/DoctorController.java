package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.dto.CreateDoctorCommand;
import com.miszunXD.medicalclinic.dto.DoctorDto;
import com.miszunXD.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDto> getDoctors() {
        log.info("REQUEST GET /doctors");
        List<DoctorDto> response = doctorService.getDoctors();
        log.info("RESPONSE GET /doctors | 200 | count={}", response.size());
        return response;
    }

    @GetMapping("/{id}")
    public DoctorDto getDoctorById(@PathVariable Long id) {
        log.info("REQUEST GET /doctor/{}", id);
        DoctorDto response = doctorService.getDoctorById(id);
        log.info("RESPONSE GET /doctor/{} | 200", id);
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto addDoctor(@RequestBody CreateDoctorCommand command) {
        log.info("REQUEST POST /doctors");
        DoctorDto response = doctorService.addDoctor(command);
        log.info("RESPONSE POST /doctors | 201");
        return response;
    }

    @PostMapping("/{doctorId}/facilities/{facilityId}")
    public DoctorDto assignFacility(@PathVariable Long doctorId, @PathVariable Long facilityId) {
        log.info("REQUEST POST /doctors/{}/facilities/{}", doctorId, facilityId);
        DoctorDto response = doctorService.assignFacility(doctorId, facilityId);
        log.info("RESPONSE POST /doctors/{}/facilities/{} | 200", doctorId, facilityId);
        return response;
    }
}
