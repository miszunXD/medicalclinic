package com.miszunXD.medicalclinic.validator;

import com.miszunXD.medicalclinic.exception.InvalidDateException;
import com.miszunXD.medicalclinic.exception.PatientNotFoundException;
import com.miszunXD.medicalclinic.exception.VisitAlreadyBookedException;
import com.miszunXD.medicalclinic.exception.VisitOverlapException;
import com.miszunXD.medicalclinic.model.Visit;
import com.miszunXD.medicalclinic.repository.PatientRepository;
import com.miszunXD.medicalclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VisitValidator {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;

    public void validateEndIsAfterStart(LocalDateTime startDate, LocalDateTime endDate) {
        if (!endDate.isAfter(startDate)) {
            throw new InvalidDateException("Visit end date: " + endDate + " has to be after start date "
                    + startDate + " !");
        }
    }

    public void validateVisitStartTimeIsNotInThePast(LocalDateTime startDate) {
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new InvalidDateException("Visit cannot be booked in the past: " + startDate + " !");
        }
    }

    public void validateTimeFullQuarter(LocalDateTime dateTime) {
        if (dateTime.getMinute() % 15 != 0 || dateTime.getSecond() != 0 || dateTime.getNano() != 0) {
            throw new InvalidDateException("Visit must be booked to full quarter of an hour: " + dateTime + " !");
        }
    }

    public void validateDoctorHasNoOverlappingVisit(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        if (visitRepository.existsByDoctorIdAndStartDateLessThanAndEndDateGreaterThan(doctorId, endDate, startDate)) {
            throw new VisitOverlapException("Doctor with given id: " + doctorId + " already has a booked visit between "
                    + startDate + " and " + endDate + " !");
        }
    }

    public void validateVisitIsNotAlreadyBooked(Visit visit) {
        if (visit.isTaken()) {
            throw new VisitAlreadyBookedException("Visit with given id: " + visit.getId() + " is already booked!");
        }
    }

    public void validatePatientExists(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient with given id: " + id + " not found!");
        }
    }
}
