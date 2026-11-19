package com.miszunXD.medicalclinic.mapper;

import com.miszunXD.medicalclinic.dto.CreatePatientCommand;
import com.miszunXD.medicalclinic.dto.PatientDto;
import com.miszunXD.medicalclinic.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    PatientDto toDto(Patient patient);

    List<PatientDto> toDtos(List<Patient> patients);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Patient toEntity(CreatePatientCommand command);
}
