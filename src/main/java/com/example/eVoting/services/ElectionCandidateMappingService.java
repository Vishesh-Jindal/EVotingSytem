package com.example.eVoting.services;

import com.example.eVoting.entities.ElectionCandidateMapping;
import com.example.eVoting.exceptions.NotFoundException;
import com.example.eVoting.repositories.ElectionCandidateMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionCandidateMappingService {
    @Autowired
    ElectionCandidateMappingRepository electionCandidateMappingRepository;
    public ElectionCandidateMapping getByCandidateAndElection(Integer candidateId, Integer electionId) throws NotFoundException{
        Optional<ElectionCandidateMapping> electionCandidateMapping = electionCandidateMappingRepository.getByCandidateAndElection(candidateId, electionId);
        if(!electionCandidateMapping.isPresent()){
            throw new NotFoundException("ElectionCandidateMapping with candidateId:"+candidateId+" and electionId:"+electionId+" not found");
        }
        return electionCandidateMapping.get();
    }
    public List<ElectionCandidateMapping> getByElection(Integer electionId){
        return electionCandidateMappingRepository.getByElection(electionId);
    }
}
