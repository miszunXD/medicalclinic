package com.miszunXD.medicalclinic.service;

import com.miszunXD.medicalclinic.exception.UserAlreadyExistsException;
import com.miszunXD.medicalclinic.exception.UserNotFoundException;
import com.miszunXD.medicalclinic.model.User;
import com.miszunXD.medicalclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with given username already exists!");
        }
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username
                + " not found!"));
    }

    public void deleteUser(String username) {
        User user = getUserByUsername(username);
        userRepository.delete(user);
    }

    public User updateUser(String username, User updated) {
        User existing = getUserByUsername(username);
        if (!Objects.equals(username, updated.getUsername()) && userRepository.existsByUsername(updated.getUsername())) {
            throw new UserAlreadyExistsException("User with given username already exists!");
        }
        existing.update(updated);
        return existing;
    }
}
