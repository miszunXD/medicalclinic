package com.miszunXD.medicalclinic.dto;

import java.time.LocalDate;

public record CreatePatientCommand(String idCardNo, String firstName, String lastName,
                                   String phoneNumber, LocalDate birthday, Long userId) {
}
