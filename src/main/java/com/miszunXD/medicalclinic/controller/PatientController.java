package com.miszunXD.medicalclinic.controller;

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
    private final PatientService service;

    @GetMapping
    public List<Patient> findAll() {
        return service.findAll();
    }

    @GetMapping("/{email}")
    public Patient getPatientByEmail(@PathVariable String email) {
        return service.getPatientByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient addPatient(@RequestBody Patient patient) {
        return service.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        service.deletePatient(email);
    }

    @PutMapping("/{email}")
    public Patient updatePatientByEmail(@PathVariable String email, @RequestBody Patient patient) {
        return service.updatePatientByEmail(email, patient);
    }
}
