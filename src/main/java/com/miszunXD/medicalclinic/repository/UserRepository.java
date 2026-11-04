package com.miszunXD.medicalclinic.repository;

import com.miszunXD.medicalclinic.model.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

    public Optional<User> getByUsername(String username) {
        return users.stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
    }

    public void delete(User user) {
        users.remove(user);
    }

    public boolean existsByUsername(String username) {
        return users.stream()
                .anyMatch(user -> Objects.equals(user.getUsername(), username));
    }
}
