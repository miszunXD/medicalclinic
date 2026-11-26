package com.miszunXD.medicalclinic.mapper;

import com.miszunXD.medicalclinic.dto.CreateVisitCommand;
import com.miszunXD.medicalclinic.dto.VisitDto;
import com.miszunXD.medicalclinic.model.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    VisitDto toDto(Visit visit);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    Visit toEntity(CreateVisitCommand command);
}
