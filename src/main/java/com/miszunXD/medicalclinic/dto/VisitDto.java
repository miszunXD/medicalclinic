package com.miszunXD.medicalclinic.dto;

import java.time.LocalDateTime;

public record VisitDto(Long id, LocalDateTime startDate, LocalDateTime endDate, Long doctorId, Long patientId) {
}
