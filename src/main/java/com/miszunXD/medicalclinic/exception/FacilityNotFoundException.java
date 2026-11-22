package com.miszunXD.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class FacilityNotFoundException extends MedicalClinicException {
    public FacilityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
