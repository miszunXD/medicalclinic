package com.miszunXD.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class DoctorAlreadyAssignedToFacilityException extends MedicalClinicException {
    public DoctorAlreadyAssignedToFacilityException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
