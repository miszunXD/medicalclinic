package com.miszunXD.medicalclinic.repository;

import com.miszunXD.medicalclinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUserEmail(String email);

    boolean existsByIdCardNo(String idCardNo);

    boolean existsByUserId(Long userId);
}
