package com.miszunXD.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class VisitOverlapException extends MedicalClinicException {
    public VisitOverlapException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
