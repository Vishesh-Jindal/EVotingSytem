package com.example.eVoting.controllers;

import com.example.eVoting.dto.RegisterRequest;
import com.example.eVoting.dto.RoleResponse;
import com.example.eVoting.dto.UserResponse;
import com.example.eVoting.dto.VoteResponse;
import com.example.eVoting.exceptions.AlreadyExistsException;
import com.example.eVoting.exceptions.NotFoundException;
import com.example.eVoting.exceptions.VotingNotAllowedException;
import com.example.eVoting.services.ElectionService;
import com.example.eVoting.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ElectionService electionService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> doRegister(@RequestBody @Valid RegisterRequest registerRequest){
        log.info("Request received to register user:"+ registerRequest.getUsername());
        try{
            UserResponse response = userService.registerUser(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (AlreadyExistsException alreadyExistsException){
            throw alreadyExistsException;
        }
    }
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username){
        log.info("Request received to get user:"+username);
        try{
            UserResponse response = userService.getUser(username);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (NotFoundException notFoundException){
            throw notFoundException;
        }
    }
    @GetMapping("/{username}/roles")
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    public ResponseEntity<RoleResponse> getUserRoles(@PathVariable("username") String username){
        log.info("Request received to get roles for user:"+username);
        try{
            RoleResponse response = userService.getUserRoles(username);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (NotFoundException notFoundException){
            throw notFoundException;
        }
    }
    @PostMapping("/{username}/election/{electionId}/candidate/{candidateId}")
    @PreAuthorize("hasRole('ROLE_VOTER')")
    public ResponseEntity<VoteResponse> addVote(@PathVariable("electionId") Integer electionId, @PathVariable("candidateId") Integer candidateId, @PathVariable("username") String username) {
        log.info("Request received to add vote by user:" + username + " in election:" + electionId + " for candidate:" + candidateId);
        try {
            VoteResponse voteResponse = electionService.addVote(electionId, candidateId, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(voteResponse);
        } catch (VotingNotAllowedException votingNotAllowedException){
            throw votingNotAllowedException;
        } catch (NotFoundException notFoundException) {
            throw notFoundException;
        }
    }
}
