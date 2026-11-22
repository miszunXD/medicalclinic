package com.miszunXD.medicalclinic.repository;

import com.miszunXD.medicalclinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    boolean existsByDoctorIdAndStartDateLessThanAndEndDateGreaterThan(Long doctorId, LocalDateTime endDate,
                                                                      LocalDateTime startDate);
}
