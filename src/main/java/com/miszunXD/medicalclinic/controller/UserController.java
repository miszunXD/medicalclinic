package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.dto.CreateUserCommand;
import com.miszunXD.medicalclinic.dto.UpdateUserCommand;
import com.miszunXD.medicalclinic.dto.UserDto;
import com.miszunXD.medicalclinic.dto.ChangePasswordCommand;
import com.miszunXD.medicalclinic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<UserDto> getUsers(Pageable pageable) {
        log.info("REQUEST GET /users");
        Page<UserDto> response = userService.getUsers(pageable);
        log.info("RESPONSE GET /users | 200 | pages count={}", response.getTotalPages());
        return response;
    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        log.info("REQUEST GET /users/{}", username);
        UserDto response = userService.getUserByUsername(username);
        log.info("RESPONSE GET /users/{} | 200", username);
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody CreateUserCommand command) {
        log.info("REQUEST POST /users");
        UserDto response = userService.addUser(command);
        log.info("RESPONSE POST /users | 201");
        return response;
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String username) {
        log.info("REQUEST DELETE /users/{}", username);
        userService.deleteUser(username);
        log.info("RESPONSE DELETE /users/{} | 204", username);
    }

    @PutMapping("/{username}")
    public UserDto updateUser(@PathVariable String username, @RequestBody UpdateUserCommand command) {
        log.info("REQUEST PUT /users/{}", username);
        UserDto response = userService.updateUser(username, command);
        log.info("RESPONSE PUT /users/{} | 200", username);
        return response;
    }

    @PatchMapping("/{username}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable String username, @RequestBody ChangePasswordCommand command) {
        log.info("REQUEST PATCH /users/{}/password", username);
        userService.changePassword(username, command);
        log.info("RESPONSE PATCH /users/{}/password | 204", username);
    }
}
