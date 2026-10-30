package com.miszunXD.medicalclinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    public void update(Patient updated) {
        this.email = updated.getEmail();
        this.firstName = updated.getFirstName();
        this.lastName = updated.getLastName();
        this.phoneNumber = updated.getPhoneNumber();
        this.birthday = updated.getBirthday();
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
