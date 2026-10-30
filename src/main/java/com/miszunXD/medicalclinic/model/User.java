package com.miszunXD.medicalclinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String username;
    private String password;

    public void update(User updated) {
        this.username = updated.getUsername();
    }
}
