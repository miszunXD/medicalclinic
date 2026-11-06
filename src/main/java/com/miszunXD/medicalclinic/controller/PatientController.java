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
        log.info("REQUEST GET /patients/");
        List<PatientDto> response = patientService.getPatients();
        log.info("RESPONSE GET /patients/ {} patients", response.size());
        return response;
    }

    @GetMapping("/{email}")
    public PatientDto getPatientByEmail(@PathVariable String email) {
        log.info("REQUEST: GET /patients/email: {}", email);
        PatientDto response = patientService.getPatientByEmail(email);
        log.info("RESPONSE GET /patients/email: {} | 200", email);
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto addPatient(@RequestBody CreatePatientCommand command) {
        log.info("REQUEST POST /patients/ body: {}", command);
        PatientDto response = patientService.addPatient(command);
        log.info("RESPONSE POST /patients/ response: {} | 201", response);
        return response;
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        log.info("REQUEST DELETE /patients/ email: {}", email);
        patientService.deletePatient(email);
        log.info("RESPONSE DELETE /patients/ email: {} | 204", email);
    }

    @PutMapping("/{email}")
    public PatientDto updatePatientByEmail(@PathVariable String email, @RequestBody PatientDto patient) {
        log.info("REQUEST PUT /patients/email: {} | body: {}", email, patient );
        PatientDto response =  patientService.updatePatientByEmail(email, patient);
        log.info("RESPONSE PUT /patients/email: {} | body: {}", email, response);
        return response;
    }

    @PatchMapping("/{email}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable String email, @RequestBody ChangePasswordCommand command) {
        log.info("REQUEST PATCH /patients/email: {} | body: {}", email, command);
        patientService.changePassword(email, command);
        log.info("RESPONSE PATCH /patients/email: {} | 204", email);
    }
}
