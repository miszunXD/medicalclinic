package com.miszunXD.medicalclinic.dto;

import java.util.List;

public record DoctorDto(Long id, String email, String firstName, String lastName, String specialty, Long userId,
                        List<FacilityDto> facilities) {
}
