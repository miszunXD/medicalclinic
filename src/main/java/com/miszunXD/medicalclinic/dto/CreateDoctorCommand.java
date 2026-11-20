package com.miszunXD.medicalclinic.dto;

public record CreateDoctorCommand(String firstName, String lastName, String specialty, Long userId) {
}
