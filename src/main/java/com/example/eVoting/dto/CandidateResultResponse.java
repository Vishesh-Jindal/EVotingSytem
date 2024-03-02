package com.example.eVoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResultResponse {
    String username;
    String name;
    Integer numVotes;
}
