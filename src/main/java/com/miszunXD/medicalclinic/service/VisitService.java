package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.dto.CreateVisitCommand;
import com.miszunXD.medicalclinic.dto.VisitDto;
import com.miszunXD.medicalclinic.exception.*;
import com.miszunXD.medicalclinic.mapper.VisitMapper;
import com.miszunXD.medicalclinic.model.Doctor;
import com.miszunXD.medicalclinic.model.Patient;
import com.miszunXD.medicalclinic.model.Visit;
import com.miszunXD.medicalclinic.repository.DoctorRepository;
import com.miszunXD.medicalclinic.repository.PatientRepository;
import com.miszunXD.medicalclinic.repository.VisitRepository;
import com.miszunXD.medicalclinic.validator.VisitValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitService {

    private final VisitMapper visitMapper;
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public VisitDto createVisit(CreateVisitCommand command) {
        log.info("Create visit: {}", command);
        VisitValidator.validateVisitTime(command.startDate(), command.endDate());
        Doctor doctor = findDoctorById(command.doctorId());
        validateDoctorHasNoOverlappingVisit(command.doctorId(), command.startDate(), command.endDate());
        Visit visit = visitMapper.toEntity(command);
        visit.setDoctor(doctor);
        Visit saved = visitRepository.save(visit);
        log.info("Visit created, id: {}", saved.getId());
        return visitMapper.toDto(saved);
    }

    @Transactional
    public VisitDto registerPatient(Long visitId, Long patientId) {
        log.info("Register patient: {} visit: {}", patientId, visitId);
        Visit visit = findVisitById(visitId);
        Patient patient = findPatientById(patientId);
        VisitValidator.validateVisitStartTimeIsNotInThePast(visit.getStartDate());
        VisitValidator.validateVisitIsNotAlreadyBooked(visit);
        visit.assignPatient(patient);
        Visit saved = visitRepository.save(visit);
        log.info("Patient: {} registered for visit: {}", patientId, saved.getId());
        return visitMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<VisitDto> getPatientVisits(Long patientId) {
        log.info("Get all visits for patient: {}", patientId);
        validatePatientExists(patientId);
        List<VisitDto> result = visitMapper.toDtos(visitRepository.findAllByPatientId(patientId));
        log.info("Number of: {} visits registered by patient: {}", result.size(), patientId);
        return result;
    }

    private Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with given id: " + id + " not found!"));
    }

    private Visit findVisitById(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit with given id: " + id + " not found!"));
    }

    private Patient findPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given id: " + id + " not found!"));
    }

    private void validateDoctorHasNoOverlappingVisit(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        if (visitRepository.existsByDoctorIdAndStartDateLessThanAndEndDateGreaterThan(doctorId, endDate, startDate)) {
            throw new VisitOverlapException("Doctor with given id: " + doctorId + " already has a booked visit between "
                    + startDate + " and " + endDate + " !");
        }
    }

    private void validatePatientExists(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient with given id: " + id + " not found!");
        }
    }
}
