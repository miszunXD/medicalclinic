package com.miszunXD.medicalclinic.dto;

public record CreateFacilityCommand(String name, String city, String postalCode, String street, String streetNumber) {
}
