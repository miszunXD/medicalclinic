package com.miszunXD.medicalclinic.dto;

import java.time.LocalDate;

public record CreatePatientCommand(String email, String password, String idCardNo, String firstName, String lastName,
                                   String phoneNumber, LocalDate birthday) {
}
