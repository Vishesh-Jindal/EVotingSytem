package com.example.eVoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectionRequest {
    private String name;
    private Set<String> candidatesUserNames;
    private LocalDate startDate;
    private LocalDate endDate;
}
