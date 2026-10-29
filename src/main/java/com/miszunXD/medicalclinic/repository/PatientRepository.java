package com.miszunXD.medicalclinic.repository;

import com.miszunXD.medicalclinic.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public Patient save(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public void delete(Patient patient) {
        patients.remove(patient);
    }

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(patient -> Objects.equals(patient.getEmail(), email))
                .findFirst();
    }

    public boolean existsByEmail(String email) {
        return patients.stream()
                .anyMatch(patient -> Objects.equals(patient.getEmail(), email));
    }
}
