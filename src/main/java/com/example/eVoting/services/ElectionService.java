package com.example.eVoting.services;

import com.example.eVoting.dto.*;
import com.example.eVoting.entities.Election;
import com.example.eVoting.entities.ElectionCandidateMapping;
import com.example.eVoting.entities.User;
import com.example.eVoting.entities.VoteElectionCandidateMapping;
import com.example.eVoting.exceptions.AlreadyExistsException;
import com.example.eVoting.exceptions.NotFoundException;
import com.example.eVoting.exceptions.ResultsNotAllowedException;
import com.example.eVoting.exceptions.VotingNotAllowedException;
import com.example.eVoting.repositories.ElectionRepository;
import com.example.eVoting.repositories.UserRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElectionService {
    @Autowired
    ElectionRepository electionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ElectionCandidateMappingService electionCandidateMappingService;
    @Autowired
    VoteElectionCandidateMappingService voteElectionCandidateMappingService;
    public ElectionResponse addElection(ElectionRequest electionRequest) throws AlreadyExistsException{
        Optional<Election> electionOptional = electionRepository.findByName(electionRequest.getName());
        if(electionOptional.isPresent()){
            throw new AlreadyExistsException("Election with election name:"+electionRequest.getName()+" already exists");
        }
        Election election = new Election();
        election.setName(electionRequest.getName());
        election.setStartDate(electionRequest.getStartDate());
        election.setEndDate(electionRequest.getEndDate());
        Set<String> candidatesUserNames = electionRequest.getCandidatesUserNames();
        for(String candidateUserName:candidatesUserNames){
            User candidate=userRepository.findByUsername(candidateUserName).orElseThrow(() -> new NotFoundException("Candidate with username:"+candidateUserName+" doesn't exists"));
            ElectionCandidateMapping electionCandidateMapping = new ElectionCandidateMapping();
            electionCandidateMapping.setElection(election);
            electionCandidateMapping.setCandidate(candidate);
            election.getElectionCandidateMappingSet().add(electionCandidateMapping);
        }
        Election createdElection = electionRepository.save(election);
        return ElectionResponse.builder()
                .id(createdElection.getId())
                .name(createdElection.getName())
                .startDate(createdElection.getStartDate())
                .endDate(createdElection.getEndDate())
                .candidates(
                        createdElection.getElectionCandidateMappingSet()
                                .stream().map(
                                        (electionCandidateMapping) -> CandidateResponse.builder()
                                                .id(electionCandidateMapping.getCandidate().getId())
                                                .username(electionCandidateMapping.getCandidate().getUsername())
                                                .name(electionCandidateMapping.getCandidate().getName())
                                                .gender(electionCandidateMapping.getCandidate().getGender())
                                                .build()
                                        ).collect(Collectors.toSet())
                ).build();
    }
    public ElectionResponse getElection(Integer electionId) throws NotFoundException {
        Optional<Election> electionOptional = electionRepository.findById(electionId);
        if(!electionOptional.isPresent()){
            throw new NotFoundException("Election with electionId:"+electionId+" not found");
        }
        Election election = electionOptional.get();
        return ElectionResponse.builder()
                .id(election.getId())
                .name(election.getName())
                .startDate(election.getStartDate())
                .endDate(election.getEndDate())
                .candidates(
                        election.getElectionCandidateMappingSet()
                                .stream().map(
                                        (electionCandidateMapping) -> CandidateResponse.builder()
                                                .id(electionCandidateMapping.getCandidate().getId())
                                                .username(electionCandidateMapping.getCandidate().getUsername())
                                                .name(electionCandidateMapping.getCandidate().getName())
                                                .gender(electionCandidateMapping.getCandidate().getGender())
                                                .build()
                                ).collect(Collectors.toSet())
                ).build();
    }
    public List<ElectionResponse> getAllElections(){
        List<Election> electionList = electionRepository.findAll();
        List<ElectionResponse> electionResponses = new ArrayList<>();
        for(Election election:electionList){
            electionResponses.add(
                    ElectionResponse.builder()
                            .id(election.getId())
                            .name(election.getName())
                            .startDate(election.getStartDate())
                            .endDate(election.getEndDate())
                            .build()
            );
        }
        return electionResponses;
    }
    public ElectionResultResponse getElectionResult(Integer electionId) throws ResultsNotAllowedException, NotFoundException{
        List<ElectionCandidateMapping> electionCandidateMappings = electionCandidateMappingService.getByElection(electionId);
        if(electionCandidateMappings.size()==0){
            throw new NotFoundException("Election Candidates with election Id:"+electionId+" doesn't exists");
        }

        LocalDate startDate = electionCandidateMappings.get(0).getElection().getStartDate();
        LocalDate endDate = electionCandidateMappings.get(0).getElection().getEndDate();
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        if((now.isBefore(endDate) && now.isAfter(startDate)) || now.isEqual(endDate) || now.isEqual(startDate)) throw new ResultsNotAllowedException("Voting is in progress, can't fetch Election Results");

        Set<CandidateResultResponse> candidateResultResponseSet = new HashSet<>();
        for(ElectionCandidateMapping electionCandidateMapping:electionCandidateMappings){
            Integer count = voteElectionCandidateMappingService.getCountByElectionCandidateMapping(electionCandidateMapping.getId());
            candidateResultResponseSet.add(
                    CandidateResultResponse.builder()
                        .username(electionCandidateMapping.getCandidate().getUsername())
                        .numVotes(count)
                        .name(electionCandidateMapping.getCandidate().getName())
                        .build()
            );
        }
        return ElectionResultResponse.builder()
                .electionName(electionCandidateMappings.get(0).getElection().getName())
                .candidateResultResponseSet(candidateResultResponseSet)
                .build();
    }
    public VoteResponse addVote(Integer electionId, Integer candidateId, String username) throws NotFoundException, VotingNotAllowedException {
        ElectionCandidateMapping electionCandidateMapping = electionCandidateMappingService.getByCandidateAndElection(candidateId, electionId);
        LocalDate startDate = electionCandidateMapping.getElection().getStartDate();
        LocalDate endDate = electionCandidateMapping.getElection().getEndDate();
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        if(now.isBefore(startDate)) throw new VotingNotAllowedException("Voting is yet to start");
        else if(now.isAfter(endDate)) throw new VotingNotAllowedException("Voting has ended");
        VoteElectionCandidateMapping voteElectionCandidateMapping = new VoteElectionCandidateMapping();
        voteElectionCandidateMapping.setElection(electionCandidateMapping.getElection());
        voteElectionCandidateMapping.setElectionCandidateMapping(electionCandidateMapping);
        voteElectionCandidateMapping.setUser(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User with username:"+username+" doesn't exists")));
        return voteElectionCandidateMappingService.addVoteElectionCandidateMapping(voteElectionCandidateMapping);
    }
}
