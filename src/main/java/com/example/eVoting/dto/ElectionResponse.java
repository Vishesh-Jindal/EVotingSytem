package com.example.eVoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectionResponse {
    Integer id;
    String name;
    Set<CandidateResponse> candidates;
    LocalDate startDate;
    LocalDate endDate;
}
