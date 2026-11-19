package com.miszunXD.medicalclinic.dto;

import java.time.LocalDate;

public record UpdatePatientCommand(String idCardNo, String firstName, String lastName, String phoneNumber,
                                   LocalDate birthday) {
}
