package com.example.eVoting.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "voteElectionCandidateMapping",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "electionId"}))
@Getter
@Setter
public class VoteElectionCandidateMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electionCandidateMappingId")
    ElectionCandidateMapping electionCandidateMapping;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;

    @ManyToOne
    @JoinColumn(name = "electionId")
    @JsonIgnore
    Election election;
}
