package com.miszunXD.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class IdCardNumberChangeNotAllowedException extends MedicalClinicException {
    public IdCardNumberChangeNotAllowedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
