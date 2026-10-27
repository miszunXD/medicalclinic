package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.model.Patient;
import com.miszunXD.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/patients") // wszystkie endpointy zaczynaja sie w klasie /patients

@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

@GetMapping
    List<Patient> getPatients() {
    return patientService.getPatients();
}
}
