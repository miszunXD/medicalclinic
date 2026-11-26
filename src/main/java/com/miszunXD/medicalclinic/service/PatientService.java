package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.dto.CreatePatientCommand;
import com.miszunXD.medicalclinic.dto.PatientDto;
import com.miszunXD.medicalclinic.dto.UpdatePatientCommand;
import com.miszunXD.medicalclinic.exception.*;
import com.miszunXD.medicalclinic.mapper.PatientMapper;
import com.miszunXD.medicalclinic.model.Patient;
import com.miszunXD.medicalclinic.model.User;
import com.miszunXD.medicalclinic.repository.PatientRepository;
import com.miszunXD.medicalclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<PatientDto> getPatients(Pageable pageable) {
        log.info("Getting Page/patients");
        Page<PatientDto> result = patientRepository.findAll(pageable)
                        .map(patientMapper::toDto);
        log.info("List of {} patients", result.getTotalPages());
        return result;
    }

    @Transactional(readOnly = true)
    public PatientDto getPatientByEmail(String email) {
        log.info("GET patient by email {}", email);
        PatientDto result = patientMapper.toDto(findPatientByEmail(email));
        log.info("Found patient by email, idCardNo: {}", result.idCardNo());
        return result;
    }

    @Transactional
    public PatientDto addPatient(CreatePatientCommand command) {
        log.info("Add new patient: {}", command);
        validateIdCardNoIsUnique(command.idCardNo());
        validateUsernameIsUnique(command.username());
        validateEmailIsUnique(command.email());
        User user = createUserFromPatientCommand(command);
        Patient patient = patientMapper.toEntity(command);
        patient.setUser(user);
        Patient saved = patientRepository.save(patient);
        log.info("Added new patient, idCardNo: {}, userId: {}", saved.getIdCardNo(), saved.getUser().getId());
        return patientMapper.toDto(saved);
    }

    @Transactional
    public void deletePatient(String email) {
        log.info("Delete patient by email: {}", email);
        Patient patient = findPatientByEmail(email);
        patientRepository.delete(patient);
        log.info("Patient deleted from database, idCardNo: {}", patient.getIdCardNo());
    }

    @Transactional
    public PatientDto updatePatientByEmail(String email, UpdatePatientCommand command) {
        log.info("Update patient by email: {}, body: {}", email, command);
        Patient existing = findPatientByEmail(email);
        validateIdCardNumberNotChanged(existing.getIdCardNo(), command.idCardNo());
        existing.update(command.firstName(), command.lastName(), command.phoneNumber(),
                command.birthday());
        Patient saved = patientRepository.save(existing);
        log.info("Patient updated, idCardNo: {}", saved.getIdCardNo());
        return patientMapper.toDto(saved);
    }

    private User createUserFromPatientCommand(CreatePatientCommand command) {
        User user = new User();
        user.setEmail(command.email());
        user.setUsername(command.username());
        user.setPassword(command.password());
        return user;
    }

    private void validateIdCardNumberNotChanged(String currentIdCardNo, String updatedIdCardNo) {
        if (!Objects.equals(currentIdCardNo, updatedIdCardNo)) {
            throw new IdCardNumberChangeNotAllowedException("Not allowed to change IDCard number!");
        }
    }

    private Patient findPatientByEmail(String email) {
        return patientRepository.findByUserEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email: " + email + " not found!"));
    }

    private void validateIdCardNoIsUnique(String idCardNo) {
        if (patientRepository.existsByIdCardNo(idCardNo)) {
            throw new PatientAlreadyExistsException("Patient with idCardNo: " + idCardNo
                    + " already exists!");
        }
    }

    private void validateUsernameIsUnique(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with given username: " + username + " already exists!");
        }
    }

    private void validateEmailIsUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with given email: " + email + " already exists!");
        }
    }
}
