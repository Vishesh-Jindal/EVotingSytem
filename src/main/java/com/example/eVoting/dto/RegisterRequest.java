package com.example.eVoting.dto;

import com.example.eVoting.enums.Gender;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
public class RegisterRequest {
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull
    private LocalDate dob;
    private String address;
}
