package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.dto.CreatePatientCommand;
import com.miszunXD.medicalclinic.dto.PatientDto;
import com.miszunXD.medicalclinic.exception.IdCardNumberChangeNotAllowedException;
import com.miszunXD.medicalclinic.exception.PatientAlreadyExistsException;
import com.miszunXD.medicalclinic.exception.PatientNotFoundException;
import com.miszunXD.medicalclinic.mapper.PatientMapper;
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
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getPatients() {
        return patientMapper.toDtoToList(patientRepository.getPatients());
    }

    public PatientDto getPatientByEmail(String email) {
        return patientMapper.toDto(findPatientByEmail(email));
    }

    public PatientDto addPatient(CreatePatientCommand command) {
        if (patientRepository.existsByEmail(command.email())) {
            throw new PatientAlreadyExistsException("Patient with given email: " + command.email()
                    + " already exists!");
        }
        Patient saved = patientRepository.save(patientMapper.toEntity(command));
        return patientMapper.toDto(saved);
    }

    public void deletePatient(String email) {
        Patient patient = findPatientByEmail(email);
        patientRepository.delete(patient);
    }

    public PatientDto updatePatientByEmail(String email, PatientDto updated) {
        Patient existing = findPatientByEmail(email);
        if (!Objects.equals(existing.getIdCardNo(), updated.idCardNo())) {
            throw new IdCardNumberChangeNotAllowedException("Not allowed to change IDCard number!");
        }
        if (!Objects.equals(email, updated.email()) && patientRepository.existsByEmail(updated.email())) {
            throw new PatientAlreadyExistsException("Patient with given email: " + updated.email()
                    + " already exists!");
        }
        patientMapper.updateFromDto(updated, existing);
        return patientMapper.toDto(existing);
    }

    public void changePassword(String email, ChangePasswordCommand command) {
        Patient patient = findPatientByEmail(email);
        patient.changePassword(command.getNewPassword());
    }

    private Patient findPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email: " + email + " not found!"));
    }
}
