package com.example.eVoting.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "election", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Getter
@Setter
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "startDate")
    LocalDate startDate;
    @Column(name = "endDate")
    LocalDate endDate;
    @OneToMany(mappedBy = "election", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JsonIgnore
    Set<ElectionCandidateMapping> electionCandidateMappingSet = new HashSet<>();
}
