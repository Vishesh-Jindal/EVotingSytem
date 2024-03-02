package com.example.eVoting.controllers;

import com.example.eVoting.dto.ElectionResponse;
import com.example.eVoting.dto.ElectionResultResponse;
import com.example.eVoting.exceptions.NotFoundException;
import com.example.eVoting.exceptions.ResultsException;
import com.example.eVoting.services.ElectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/election")
@Slf4j
@PreAuthorize("hasRole('ROLE_NORMAL')")
public class ElectionController {
    @Autowired
    ElectionService electionService;

    @GetMapping("/{electionId}")
    public ResponseEntity<ElectionResponse> getElection(@PathVariable("electionId") Integer electionId){
        log.info("Request received to get election:"+electionId);
        try{
            ElectionResponse response = electionService.getElection(electionId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (NotFoundException notFoundException){
            throw notFoundException;
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<ElectionResponse>> getAllElections(){
        log.info("Request received to get all elections");
        try{
            List<ElectionResponse> response = electionService.getAllElections();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (NotFoundException notFoundException){
            throw notFoundException;
        }
    }
    @GetMapping("/{electionId}/results")
    public ResponseEntity<ElectionResultResponse> getElectionResult(@PathVariable("electionId") Integer electionId){
        log.info("Request received to get election results for election:"+electionId);
        try{
            ElectionResultResponse response = electionService.getElectionResult(electionId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException notFoundException){
            throw notFoundException;
        } catch (ResultsException resultsNotAllowedException){
            throw resultsNotAllowedException;
        }
    }
}
