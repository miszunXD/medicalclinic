package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.exception.PatientAlreadyExistsException;
import com.miszunXD.medicalclinic.exception.PatientNotFoundException;
import com.miszunXD.medicalclinic.model.Patient;
import com.miszunXD.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;

    public List<Patient> findAll() {
        return repository.findAll();
    }

    public Patient getPatientByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Nie znaleziono pacjenta z adresem email: " + email));
    }

    public Patient addPatient(Patient patient) {
        if (repository.findByEmail(patient.getEmail()).isPresent()) {
            throw new PatientAlreadyExistsException("Pacjent z adresem email: " + patient.getEmail()
                    + " już istnieje w bazie!");
        }
        return repository.save(patient);
    }

    public void deletePatient(String email) {
        repository.deleteByEmail(email);
    }

    public Patient updatePatientByEmail(String email, Patient updated) {
        Patient existing = getPatientByEmail(email);
        existing.setPassword(updated.getPassword());
        existing.setIdCardNo(updated.getIdCardNo());
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setBirthday(updated.getBirthday());
        return existing;
    }
}
