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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getPatients() {
        log.info("Getting List/patients");
        List<PatientDto> result = patientMapper.toDtoToList(patientRepository.getPatients());
        log.info("List of {} patients", result.size());
        return result;
    }

    public PatientDto getPatientByEmail(String email) {
        log.info("GET patient by email {}", email);
        PatientDto result =  patientMapper.toDto(findPatientByEmail(email));
        log.info("Found patient by email, idCardNo: {}", result.idCardNo());
        return result;
    }

    public PatientDto addPatient(CreatePatientCommand command) {
        log.info("Add new patient: {}", command);
        if (patientRepository.existsByEmail(command.email())) {
            throw new PatientAlreadyExistsException("Patient with given email: " + command.email()
                    + " already exists!");
        }
        Patient saved = patientRepository.save(patientMapper.toEntity(command));
        log.info("Added new patient, idCardNo: {}", saved.getIdCardNo());
        return patientMapper.toDto(saved);
    }

    public void deletePatient(String email) {
        log.info("Delete patient by email: {}", email);
        Patient patient = findPatientByEmail(email);
        patientRepository.delete(patient);
        log.info("Patient deleted from database, idCardNo: {}", patient.getIdCardNo());
    }

    public PatientDto updatePatientByEmail(String email, PatientDto updated) {
        log.info("Update patient by email: {}, body: {}", email, updated);
        Patient existing = findPatientByEmail(email);
        if (!Objects.equals(existing.getIdCardNo(), updated.idCardNo())) {
            throw new IdCardNumberChangeNotAllowedException("Not allowed to change IDCard number!");
        }
        if (!Objects.equals(email, updated.email()) && patientRepository.existsByEmail(updated.email())) {
            throw new PatientAlreadyExistsException("Patient with given email: " + updated.email()
                    + " already exists!");
        }
        patientMapper.updateFromDto(updated, existing);
        log.info("Patient updated, idCardNo: {}", existing.getIdCardNo());
        return patientMapper.toDto(existing);
    }

    public void changePassword(String email, ChangePasswordCommand command) {
        log.info("Change password: {}", email);
        Patient patient = findPatientByEmail(email);
        patient.changePassword(command.getNewPassword());
        log.info("Password changed, idCardNo: {}", patient.getIdCardNo());
    }

    private Patient findPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email: " + email + " not found!"));
    }
}
