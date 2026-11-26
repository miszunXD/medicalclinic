package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.dto.*;
import com.miszunXD.medicalclinic.exception.UserAlreadyExistsException;
import com.miszunXD.medicalclinic.exception.UserNotFoundException;
import com.miszunXD.medicalclinic.mapper.UserMapper;
import com.miszunXD.medicalclinic.model.User;
import com.miszunXD.medicalclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public PageDto<UserDto> getUsers(Pageable pageable) {
        log.info("Getting List/users");
        Page<UserDto> result = userRepository.findAll(pageable)
                        .map(userMapper::toDto);
        log.info("Found {} users on page {} of {}", result.getNumberOfElements(), result.getNumber(),
                result.getTotalPages());
        return PageDto.from(result);
    }

    @Transactional
    public UserDto addUser(CreateUserCommand command) {
        log.info("Add new user {}", command);
        validateUsernameIsUnique(command.username());
        validateEmailIsUnique(command.email());
        User saved = userRepository.save(userMapper.toEntity(command));
        log.info("Added new user, id: {}", saved.getId());
        return userMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        log.info("GET user by username: {}", username);
        UserDto result = userMapper.toDto(findByUsername(username));
        log.info("Found user by username, id: {}", result.id());
        return result;
    }

    @Transactional
    public void deleteUser(String username) {
        log.info("Delete user by username: {}", username);
        User user = findByUsername(username);
        userRepository.delete(user);
        log.info("User deleted, id: {}", user.getId());
    }

    @Transactional
    public UserDto updateUser(String username, UpdateUserCommand command) {
        log.info("Update user by username: {}, body: {}", username, command);
        User existing = findByUsername(username);
        validateNewUsernameIsUnique(username, command.username());
        validateNewEmailIsUnique(existing.getEmail(), command.email());
        existing.update(command.email(), command.username());
        User saved = userRepository.save(existing);
        log.info("User updated, id: {}", saved.getId());
        return userMapper.toDto(saved);
    }

    @Transactional
    public void changePassword(String username, ChangePasswordCommand command) {
        log.info("Change password for user: {}", username);
        User user = findByUsername(username);
        user.changePassword(command.newPassword());
        userRepository.save(user);
        log.info("Password changed, id: {}", user.getId());
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username
                        + " not found!"));
    }

    private void validateUsernameIsUnique(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with given username: " + username + " already exists!");
        }
    }

    private void validateNewUsernameIsUnique(String currentUsername, String updatedUsername) {
        if (!Objects.equals(currentUsername, updatedUsername)) {
            validateUsernameIsUnique(updatedUsername);
        }
    }

    private void validateEmailIsUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with given email: " + email + " already exists!");
        }
    }

    private void validateNewEmailIsUnique(String currentEmail, String updatedEmail) {
        if (!Objects.equals(currentEmail, updatedEmail)) {
            validateEmailIsUnique(updatedEmail);
        }
    }
}
