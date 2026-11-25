package com.miszunXD.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class VisitAlreadyBookedException extends MedicalClinicException {
    public VisitAlreadyBookedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
