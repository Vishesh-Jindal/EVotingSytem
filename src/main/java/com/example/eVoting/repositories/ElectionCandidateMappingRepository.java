package com.example.eVoting.repositories;

import com.example.eVoting.constants.Constants;
import com.example.eVoting.entities.ElectionCandidateMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ElectionCandidateMappingRepository extends JpaRepository<ElectionCandidateMapping,Integer> {
    @Query(Constants.QueryConstants.FETCH_BY_CANDIDATE_ELECTION)
    Optional<ElectionCandidateMapping> getByCandidateAndElection(@Param("candidateId") Integer candidateId, @Param("electionId") Integer electionId);
    @Query(Constants.QueryConstants.FETCH_BY_ELECTION)
    List<ElectionCandidateMapping> getByElection(@Param("electionId") Integer electionId);
}
