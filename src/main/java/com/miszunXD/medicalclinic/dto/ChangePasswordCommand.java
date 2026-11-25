package com.miszunXD.medicalclinic.dto;

public record ChangePasswordCommand(String newPassword) {
    @Override
    public String toString() {
        return "ChangePasswordCommand{new password='*****'";
    }
}
