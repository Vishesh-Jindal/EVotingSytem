package com.example.eVoting.controllers;

import com.example.eVoting.dto.*;
//import com.example.eVoting.entities.Election;
import com.example.eVoting.exceptions.AlreadyExistsException;
import com.example.eVoting.exceptions.NotFoundException;
//import com.example.eVoting.services.ElectionService;
import com.example.eVoting.exceptions.ValidationException;
import com.example.eVoting.services.ElectionService;
import com.example.eVoting.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    ElectionService electionService;
    @Autowired
    UserService userService;
    @PostMapping("/add/election")
    public ResponseEntity<ElectionResponse> addElection(@RequestBody @Valid ElectionRequest electionRequest, BindingResult bindingResult){
        log.info("Request received to add election:"+electionRequest.getName());
        if(bindingResult.hasFieldErrors()) throw new ValidationException(bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
        try{
            ElectionResponse createdElection = electionService.addElection(electionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdElection);
        }catch (AlreadyExistsException alreadyExistsException){
            throw alreadyExistsException;
        }
    }
    @PutMapping("/grant/roles/{username}")
    public ResponseEntity<RoleResponse> grantRoles(@PathVariable("username") String username, @RequestBody @Valid RoleRequest roleRequest, BindingResult bindingResult){
        // entire role list will be replaced
        log.info("Request received to add roles:"+roleRequest.getRoles()+" for user:"+username);
        if(bindingResult.hasFieldErrors()) throw new ValidationException(bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
        try{
            RoleResponse response = userService.grantRoles(username, roleRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException notFoundException){
            throw notFoundException;
        }
    }
    @PutMapping("/revoke/roles/{username}")
    public ResponseEntity<RoleResponse> revokeRoles(@PathVariable("username") String username, @RequestBody @Valid RoleRequest roleRequest, BindingResult bindingResult){
        // only mentioned roles will be revoked
        log.info("Request received to revoke roles:"+roleRequest.getRoles()+" for user:"+username);
        if(bindingResult.hasFieldErrors()) throw new ValidationException(bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
        try{
            RoleResponse response = userService.revokeRoles(username, roleRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException notFoundException){
            throw notFoundException;
        }
    }

}
