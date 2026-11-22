package com.miszunXD.medicalclinic.mapper;

import com.miszunXD.medicalclinic.dto.CreateFacilityCommand;
import com.miszunXD.medicalclinic.dto.FacilityDto;
import com.miszunXD.medicalclinic.model.Facility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

    FacilityDto toDto(Facility facility);

    List<FacilityDto> toDtos(List<Facility> facilities);

    @Mapping(target = "id", ignore = true)
    Facility toEntity(CreateFacilityCommand command);
}
