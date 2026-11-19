package com.miszunXD.medicalclinic.mapper;

import com.miszunXD.medicalclinic.dto.CreateUserCommand;
import com.miszunXD.medicalclinic.dto.UserDto;
import com.miszunXD.medicalclinic.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDtos(List<User> users);

    @Mapping(target = "id", ignore = true)
    User toEntity(CreateUserCommand command);
}
