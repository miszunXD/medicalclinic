package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.dto.CreatePatientCommand;
import com.miszunXD.medicalclinic.dto.PatientDto;
import com.miszunXD.medicalclinic.exception.*;
import com.miszunXD.medicalclinic.mapper.PatientMapper;
import com.miszunXD.medicalclinic.model.ChangePasswordCommand;
import com.miszunXD.medicalclinic.model.Patient;
import com.miszunXD.medicalclinic.model.User;
import com.miszunXD.medicalclinic.repository.PatientRepository;
import com.miszunXD.medicalclinic.repository.UserRepository;
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
    private final UserRepository userRepository;

    public List<PatientDto> getPatients() {
        log.info("Getting List/patients");
        List<PatientDto> result = patientMapper.toDtos(patientRepository.findAll());
        log.info("List of {} patients", result.size());
        return result;
    }

    public PatientDto getPatientByEmail(String email) {
        log.info("GET patient by email {}", email);
        PatientDto result = patientMapper.toDto(findPatientByEmail(email));
        log.info("Found patient by email, idCardNo: {}", result.idCardNo());
        return result;
    }

    public PatientDto addPatient(CreatePatientCommand command) {
        log.info("Add new patient: {}", command);
        validateEmailIsUnique(command.email());
        validateUserNotAssignedToPatient(command.userId());
        User user = findUserById(command.userId());
        Patient patient = patientMapper.toEntity(command);
        patient.setUser(user);
        Patient saved = patientRepository.save(patient);
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
        validateIdCardNumberNotChanged(existing.getIdCardNo(), updated.idCardNo());
        validateNewEmailIsUnique(email, updated.email());
        existing.update(updated.email(), updated.firstName(), updated.lastName(), updated.phoneNumber(),
                updated.birthday());
        Patient saved = patientRepository.save(existing);
        log.info("Patient updated, idCardNo: {}", saved.getIdCardNo());
        return patientMapper.toDto(saved);
    }

    public void changePassword(String email, ChangePasswordCommand command) {
        log.info("Change password: {}", email);
        Patient patient = findPatientByEmail(email);
        patient.changePassword(command.getNewPassword());
        patientRepository.save(patient);
        log.info("Password changed, idCardNo: {}", patient.getIdCardNo());
    }

    private void validateNewEmailIsUnique(String currentEmail, String updatedEmail) {
        if (!Objects.equals(currentEmail, updatedEmail)) {
            validateEmailIsUnique(updatedEmail);
        }
    }

    private void validateIdCardNumberNotChanged(String currentIdCardNo, String updatedIdCardNo) {
        if (!Objects.equals(currentIdCardNo, updatedIdCardNo)) {
            throw new IdCardNumberChangeNotAllowedException("Not allowed to change IDCard number!");
        }
    }

    private Patient findPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email: " + email + " not found!"));
    }

    private void validateEmailIsUnique(String email) {
        if (patientRepository.existsByEmail(email)) {
            throw new PatientAlreadyExistsException("Patient with given email: " + email
                    + " already exists!");
        }
    }

    private void validateUserNotAssignedToPatient(Long id) {
        if (patientRepository.existsByUserId(id)) {
            throw new UserAlreadyAssignedException("User with id: " + id
                    + " is already assigned to another patient!");
        }
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with given id: " + id + " not found!"));
    }
}
