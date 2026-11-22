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

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitService {

    private final VisitMapper visitMapper;
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final VisitValidator visitValidator;

    public VisitDto createVisit(CreateVisitCommand command) {
        log.info("Create visit: {}", command);
        visitValidator.validateEndIsAfterStart(command.startDate(), command.endDate());
        visitValidator.validateVisitStartTimeIsNotInThePast(command.startDate());
        visitValidator.validateTimeFullQuarter(command.startDate());
        visitValidator.validateTimeFullQuarter(command.endDate());
        Doctor doctor = findDoctorById(command.doctorId());
        visitValidator.validateDoctorHasNoOverlappingVisit(command.doctorId(), command.startDate(), command.endDate());
        Visit visit = visitMapper.toEntity(command);
        visit.setDoctor(doctor);
        Visit saved = visitRepository.save(visit);
        log.info("Visit created, id: {}", saved.getId());
        return visitMapper.toDto(saved);
    }

    public VisitDto registerPatient(Long visitId, Long patientId) {
        log.info("Register patient: {} visit: {}", patientId, visitId);
        Visit visit = findVisitById(visitId);
        Patient patient = findPatientById(patientId);
        visitValidator.validateVisitStartTimeIsNotInThePast(visit.getStartDate());
        visitValidator.validateVisitIsNotAlreadyBooked(visit);
        visit.assignPatient(patient);
        Visit saved = visitRepository.save(visit);
        log.info("Patient: {} registered for visit: {}", patientId, saved.getId());
        return visitMapper.toDto(saved);
    }

    public List<VisitDto> getPatientVisits(Long patientId) {
        log.info("Get all visits for patient: {}", patientId);
        visitValidator.validatePatientExists(patientId);
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
}
