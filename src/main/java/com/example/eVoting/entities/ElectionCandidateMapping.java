package com.example.eVoting.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "election_candidate_mapping")
@Getter
@Setter
public class ElectionCandidateMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id")
    Election election;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User candidate;
    @OneToMany(mappedBy = "electionCandidateMapping", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<VoteElectionCandidateMapping> voteElectionCandidateMappings  = new HashSet<>();
}
