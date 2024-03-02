package com.example.eVoting.dto;

import com.example.eVoting.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateResponse {
    Integer id;
    String username;
    String name;
    @Enumerated(EnumType.STRING)
    Gender gender;
}
