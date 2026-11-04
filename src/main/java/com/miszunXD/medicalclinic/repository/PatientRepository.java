package com.miszunXD.medicalclinic.repository;

import com.miszunXD.medicalclinic.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    public Patient save(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public void deleteByEmail(String email) {
        patients.removeIf(patient -> patient.getEmail().equals(email));
    }

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }
}
