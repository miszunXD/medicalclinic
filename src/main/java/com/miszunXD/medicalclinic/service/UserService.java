package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.dto.CreateUserCommand;
import com.miszunXD.medicalclinic.dto.UserDto;
import com.miszunXD.medicalclinic.exception.UserAlreadyExistsException;
import com.miszunXD.medicalclinic.exception.UserNotFoundException;
import com.miszunXD.medicalclinic.mapper.UserMapper;
import com.miszunXD.medicalclinic.model.User;
import com.miszunXD.medicalclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUsers() {
        log.info("Getting List/users");
        List<UserDto> result = userMapper.toDtoList(userRepository.findAll());
        log.info("List of {} users", result.size());
        return result;
    }

    public UserDto addUser(CreateUserCommand command) {
        log.info("Add new user {}", command);
        if (userRepository.existsByUsername(command.username())) {
            throw new UserAlreadyExistsException("User with given username already exists!");
        }
        User saved = userRepository.save(userMapper.toEntity(command));
        log.info("Added new user, id: {}", saved.getId());
        return userMapper.toDto(saved);
    }

    public UserDto getUserByUsername(String username) {
        log.info("GET user by username: {}", username);
        UserDto result =  userMapper.toDto(findByUsername(username));
        log.info("Found user by username, id: {}", result.id());
        return result;
    }

    public void deleteUser(String username) {
        log.info("Delete user by username: {}", username);
        User user = findByUsername(username);
        userRepository.delete(user);
        log.info("User deleted, id: {}", user.getId());
    }

    public UserDto updateUser(String username, UserDto updated) {
        log.info("Update user by username: {}, body: {}", username, updated);
        User existing = findByUsername(username);
        if (!Objects.equals(username, updated.username()) && userRepository.existsByUsername(updated.username())) {
            throw new UserAlreadyExistsException("User with given username already exists!");
        }
        userMapper.updateFromDto(updated, existing);
        userRepository.save(existing);
        log.info("User updated, id: {}", existing.getId());
        return userMapper.toDto(existing);
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username
                        + " not found!"));
    }
}
