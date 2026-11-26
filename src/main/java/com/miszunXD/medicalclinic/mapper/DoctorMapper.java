package com.miszunXD.medicalclinic.mapper;

import com.miszunXD.medicalclinic.dto.CreateDoctorCommand;
import com.miszunXD.medicalclinic.dto.DoctorDto;
import com.miszunXD.medicalclinic.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    DoctorDto toDto(Doctor doctor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "facilities", ignore = true)
    Doctor toEntity(CreateDoctorCommand command);
}