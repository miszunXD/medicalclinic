package com.miszunXD.medicalclinic.dto;

import java.time.LocalDateTime;

public record CreateVisitCommand(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
}
