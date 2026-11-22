package com.miszunXD.medicalclinic.dto;

import java.time.LocalDate;

public record CreatePatientCommand(String email, String username, String password, String idCardNo, String firstName,
                                   String lastName, String phoneNumber, LocalDate birthday) {

    @Override
    public String toString() {
        return "CreatePatientCommand{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
