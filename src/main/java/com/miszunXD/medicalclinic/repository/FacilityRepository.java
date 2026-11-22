package com.miszunXD.medicalclinic.repository;

import com.miszunXD.medicalclinic.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    boolean existsByName(String name);
}
