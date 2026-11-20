package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.dto.CreateDoctorCommand;
import com.miszunXD.medicalclinic.dto.DoctorDto;
import com.miszunXD.medicalclinic.exception.*;
import com.miszunXD.medicalclinic.mapper.DoctorMapper;
import com.miszunXD.medicalclinic.model.Doctor;
import com.miszunXD.medicalclinic.model.Facility;
import com.miszunXD.medicalclinic.model.User;
import com.miszunXD.medicalclinic.repository.DoctorRepository;
import com.miszunXD.medicalclinic.repository.FacilityRepository;
import com.miszunXD.medicalclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;

    public List<DoctorDto> getDoctors() {
        log.info("Getting List/doctors");
        List<DoctorDto> result = doctorMapper.toDtos(doctorRepository.findAll());
        log.info("List of {} doctors", result.size());
        return result;
    }

    public DoctorDto getDoctorById(Long id) {
        log.info("GET doctor by id: {}", id);
        return doctorMapper.toDto(findDoctorById(id));
    }

    public DoctorDto addDoctor(CreateDoctorCommand command) {
        log.info("Add new doctor {}", command);
        validateUserNotAssignedToDoctor(command.userId());
        User user = findUserById(command.userId());
        Doctor doctor = doctorMapper.toEntity(command);
        doctor.setUser(user);
        Doctor saved = doctorRepository.save(doctor);
        log.info("Added new doctor, id: {}", saved.getId());
        return doctorMapper.toDto(saved);
    }

    public DoctorDto assignFacility(Long doctorId, Long facilityId) {
        log.info("Assign facility: {} to doctor: {}", facilityId, doctorId);
        Doctor doctor = findDoctorById(doctorId);
        Facility facility = findFacilityById(facilityId);
        validateDoctorNotAssignedToFacility(doctor, facilityId);
        doctor.assignFacility(facility);
        Doctor saved = doctorRepository.save(doctor);
        log.info("Facility: {} assigned to doctor: {}", facilityId, doctorId);
        return doctorMapper.toDto(saved);
    }

    private Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with given id: " + id + " not found!"));
    }

    private void validateUserNotAssignedToDoctor(Long userId) {
        if (doctorRepository.existsByUserId(userId)) {
            throw new UserAlreadyAssignedException("User with given id: " + userId + " is already assigned to other doctor!");
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with given id: " + userId + " not found!"));
    }

    private Facility findFacilityById(Long facilityId) {
        return facilityRepository.findById(facilityId)
                .orElseThrow(() -> new FacilityNotFoundException("Facility with given id: " + facilityId + " not found!"));
    }

    private void validateDoctorNotAssignedToFacility(Doctor doctor, Long facilityId) {
        if (doctor.isAssignedToFacility(facilityId)) {
            throw new DoctorAlreadyAssignedToFacility("Doctor with given id: " + doctor.getId()
                    + " is already assigned to facility with id: " + facilityId);
        }
    }
}
