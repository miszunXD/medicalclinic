package com.miszunXD.medicalclinic.repository;

import com.miszunXD.medicalclinic.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByUserId(Long id);
}
