package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.dto.CreatePatientCommand;
import com.miszunXD.medicalclinic.dto.PatientDto;
import com.miszunXD.medicalclinic.dto.UpdatePatientCommand;
import com.miszunXD.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public Page<PatientDto> getPatients(Pageable pageable) {
        log.info("REQUEST GET /patients");
        Page<PatientDto> response = patientService.getPatients(pageable);
        log.info("RESPONSE GET /patients | 200 | page count={}", response.getTotalPages());
        return response;
    }

    @GetMapping("/{email}")
    public PatientDto getPatientByEmail(@PathVariable String email) {
        log.info("REQUEST GET /patients/{}", email);
        PatientDto response = patientService.getPatientByEmail(email);
        log.info("RESPONSE GET /patients/{} | 200", email);
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto addPatient(@RequestBody CreatePatientCommand command) {
        log.info("REQUEST POST /patients");
        PatientDto response = patientService.addPatient(command);
        log.info("RESPONSE POST /patients | 201");
        return response;
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        log.info("REQUEST DELETE /patients/{}", email);
        patientService.deletePatient(email);
        log.info("RESPONSE DELETE /patients/{} | 204", email);
    }

    @PutMapping("/{email}")
    public PatientDto updatePatientByEmail(@PathVariable String email, @RequestBody UpdatePatientCommand command) {
        log.info("REQUEST PUT /patients/{}", email);
        PatientDto response = patientService.updatePatientByEmail(email, command);
        log.info("RESPONSE PUT /patients/{} | 200", email);
        return response;
    }
}