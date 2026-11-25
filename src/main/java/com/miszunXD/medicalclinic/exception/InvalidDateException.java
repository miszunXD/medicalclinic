package com.miszunXD.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class InvalidDateException extends MedicalClinicException {
    public InvalidDateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
