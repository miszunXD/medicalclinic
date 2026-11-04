package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.exception.IdCardNumberChangeNotAllowedException;
import com.miszunXD.medicalclinic.exception.PatientAlreadyExistsException;
import com.miszunXD.medicalclinic.exception.PatientNotFoundException;
import com.miszunXD.medicalclinic.model.ChangePasswordCommand;
import com.miszunXD.medicalclinic.model.Patient;
import com.miszunXD.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;

    public List<Patient> getPatients() {
        return repository.getPatients();
    }

    public Patient getPatientByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found!"));
    }

    public Patient addPatient(Patient patient) {
        if (repository.existsByEmail(patient.getEmail())) {
            throw new PatientAlreadyExistsException("Patient with given email: " + patient.getEmail()
                    + " already exists!");
        }
        return repository.save(patient);
    }

    public void deletePatient(String email) {
        Patient patient = getPatientByEmail(email);
        repository.delete(patient);
    }

    public Patient updatePatientByEmail(String email, Patient updated) {
        Patient existing = getPatientByEmail(email);
        if (!Objects.equals(existing.getIdCardNo(), updated.getIdCardNo())) {
            throw new IdCardNumberChangeNotAllowedException("Not allowed to change IDCard number!");
        }
        if (!Objects.equals(email, updated.getEmail()) && repository.existsByEmail(updated.getEmail())) {
            throw new PatientAlreadyExistsException("Patient with given email: " + updated.getEmail()
                    + " already exists!");
        }
        existing.update(updated);
        return existing;
    }

    public void changePassword(String email, ChangePasswordCommand command) {
        Patient patient = getPatientByEmail(email);
        patient.changePassword(command.getNewPassword());
    }
}
