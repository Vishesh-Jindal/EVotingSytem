package com.example.eVoting.services;

import com.example.eVoting.dto.VoteResponse;
import com.example.eVoting.entities.VoteElectionCandidateMapping;
import com.example.eVoting.repositories.VoteElectionCandidateMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class VoteElectionCandidateMappingService {
    @Autowired
    VoteElectionCandidateMappingRepository voteElectionCandidateMappingRepository;
    public VoteResponse addVoteElectionCandidateMapping(VoteElectionCandidateMapping voteElectionCandidateMapping) {
        VoteElectionCandidateMapping createdVoteElectionCandidateMapping = voteElectionCandidateMappingRepository.save(voteElectionCandidateMapping);
        return VoteResponse.builder()
                .electionName(createdVoteElectionCandidateMapping.getElection().getName())
                .candidateUsername(createdVoteElectionCandidateMapping.getElectionCandidateMapping().getCandidate().getUsername())
                .username(createdVoteElectionCandidateMapping.getUser().getUsername())
                .build();
    }
    public Integer getCountByElectionCandidateMapping(Integer electionCandidateMappingId){
        return voteElectionCandidateMappingRepository.getCountByElectionCandidateMapping(electionCandidateMappingId);
    }
}
