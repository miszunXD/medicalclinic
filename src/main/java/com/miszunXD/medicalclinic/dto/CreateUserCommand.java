package com.miszunXD.medicalclinic.dto;

public record CreateUserCommand(String username, String password) {
    @Override
    public String toString() {
        return "CreateUserCommand{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
