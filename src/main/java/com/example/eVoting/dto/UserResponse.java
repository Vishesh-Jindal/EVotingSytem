package com.example.eVoting.dto;

import com.example.eVoting.enums.Gender;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    String username;
    String name;
    @Enumerated(EnumType.STRING)
    Gender gender;
    LocalDate dob;
    String address;
}
