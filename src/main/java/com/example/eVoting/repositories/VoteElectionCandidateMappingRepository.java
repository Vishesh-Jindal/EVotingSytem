package com.example.eVoting.repositories;

import com.example.eVoting.constants.Constants;
import com.example.eVoting.entities.VoteElectionCandidateMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.SQLIntegrityConstraintViolationException;

public interface VoteElectionCandidateMappingRepository extends JpaRepository<VoteElectionCandidateMapping, Integer> {
    @Query(Constants.QueryConstants.COUNT_BY_ELECTION_CANDIDATE_MAPPING)
    public Integer getCountByElectionCandidateMapping(@Param("electionCandidateMappingId") Integer electionCandidateMappingId);
}
