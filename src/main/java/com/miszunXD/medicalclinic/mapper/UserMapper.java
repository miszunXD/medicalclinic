package com.miszunXD.medicalclinic.mapper;

import com.miszunXD.medicalclinic.dto.CreateUserCommand;
import com.miszunXD.medicalclinic.dto.UserDto;
import com.miszunXD.medicalclinic.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDtoList(List<User> users);
    User toEntity(CreateUserCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateFromDto(UserDto dto, @MappingTarget User user);
}
