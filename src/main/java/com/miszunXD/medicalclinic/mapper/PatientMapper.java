package com.miszunXD.medicalclinic.mapper;

import com.miszunXD.medicalclinic.dto.CreatePatientCommand;
import com.miszunXD.medicalclinic.dto.PatientDto;
import com.miszunXD.medicalclinic.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDto toDto(Patient patient);
    List<PatientDto> toDtoToList(List<Patient> patients);
    Patient toEntity(CreatePatientCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idCardNo", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateFromDto(PatientDto dto, @MappingTarget Patient patient);

}
