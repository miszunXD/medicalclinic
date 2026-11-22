package com.miszunXD.medicalclinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "doctors", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patients", nullable = false)
    private Patient patient;

    public void assignPatient(Patient patient) {
        this.patient = patient;
    }
}
