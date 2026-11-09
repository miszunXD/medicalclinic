package com.miszunXD.medicalclinic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MedicalClinicException.class)
    public ResponseEntity<ErrorMessage> handle(MedicalClinicException e) {
        log.error("{} ({}): {}", e.getClass().getSimpleName(), e.getHttpStatus().value(), e.getMessage());
        ErrorMessage message = new ErrorMessage(e.getMessage(), e.getHttpStatus().value(), Instant.now());
    return ResponseEntity.status(e.getHttpStatus()).body(message);
    }
}
