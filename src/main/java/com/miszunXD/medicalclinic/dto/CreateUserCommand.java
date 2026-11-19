package com.miszunXD.medicalclinic.dto;

public record CreateUserCommand(String email, String username, String password) {
    @Override
    public String toString() {
        return "CreateUserCommand{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
