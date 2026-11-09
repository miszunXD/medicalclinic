package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.dto.CreatePatientCommand;
import com.miszunXD.medicalclinic.dto.PatientDto;
import com.miszunXD.medicalclinic.model.ChangePasswordCommand;
import com.miszunXD.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> getPatients() {
        log.info("REQUEST GET /patients");
        List<PatientDto> response = patientService.getPatients();
        log.info("RESPONSE GET /patients | 200 | count={}", response.size());
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
    public PatientDto updatePatientByEmail(@PathVariable String email, @RequestBody PatientDto patient) {
        log.info("REQUEST PUT /patients/{}", email);
        PatientDto response = patientService.updatePatientByEmail(email, patient);
        log.info("RESPONSE PUT /patients/{} | 200", email);
        return response;
    }

    @PatchMapping("/{email}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable String email, @RequestBody ChangePasswordCommand command) {
        log.info("REQUEST PATCH /patients/{}/password", email);
        patientService.changePassword(email, command);
        log.info("RESPONSE PATCH /patients/{}/password | 204", email);
    }
}