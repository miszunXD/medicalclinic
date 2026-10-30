package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.model.ChangePasswordCommand;
import com.miszunXD.medicalclinic.model.Patient;
import com.miszunXD.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public Patient getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public Patient updatePatientByEmail(@PathVariable String email, @RequestBody Patient patient) {
        return patientService.updatePatientByEmail(email, patient);
    }

    @PatchMapping("/{email}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable String email, @RequestBody ChangePasswordCommand command) {
        patientService.changePassword(email, command);
    }
}
