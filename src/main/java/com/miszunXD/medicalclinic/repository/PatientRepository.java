package com.miszunXD.medicalclinic.repository;

import com.miszunXD.medicalclinic.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientRepository {
    private List<Patient> patients = new ArrayList<>();

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }
}
