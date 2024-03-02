package com.example.eVoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectionResultResponse {
    String electionName;
    Set<CandidateResultResponse> candidateResultResponseSet;
}
